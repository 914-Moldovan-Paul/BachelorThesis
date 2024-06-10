import React, { useEffect, useState, useCallback } from 'react';
import CarListing from './CarListing';
import CarService from '../services/CarService';
import SearchBox from './SearchBox';
import UserService from '../services/UserService';
import ChatService from '../services/ChatService';
import LoadingSpinner from './LoadingSpinner';

const CarListings = ({ isMainPage, user }) => {
  const [cars, setCarListings] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [searchLoading, setSearchLoading] = useState(false);
  const [showReset, setShowReset] = useState(false);

  const fetchCarListings = useCallback(async () => {
    setLoading(true);
    try {
      const data = await CarService.getAllCarListings();
      console.log(data);
      setCarListings(data);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  }, []);

  const fetchCarListingsForUser = useCallback(async () => {
    setLoading(true);
    try {
      const data = await UserService.getAllCarListingsForUser(user.id);
      setCarListings(data);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  }, [user]);

  useEffect(() => {
    if (isMainPage) {
      fetchCarListings();
    } else {
      fetchCarListingsForUser();
    }
  }, [isMainPage, fetchCarListings, fetchCarListingsForUser]);

  const handleSearchSubmit = async (userPrompt) => {
    setSearchLoading(true);
    try {
      const response = await ChatService.sendUserPrompt(userPrompt, 1);
      const queryDto = {
        query: response
      };

      const selectedCarListings = await CarService.getCarListingsForQuery(queryDto);
      setCarListings(selectedCarListings);
      setShowReset(true);
    } catch (error) {
      console.error('Error handling search submit:', error);
      setError(error.message);
    } finally {
      setSearchLoading(false);
    }
  };

  const handleReset = async () => {
    setShowReset(false);
    setLoading(true);
    await fetchCarListings();
    setLoading(false);
  };

  if (loading) {
    return  <>
              <div className="flex justify-center pt-6">
                <div className="pr-2">
                  <LoadingSpinner/>
                </div>
                <p>Loading results...</p>
              </div>
            </>
  }

  if (error) {
    return <p>Error: {error}</p>;
  }

  return (
    <div>
      {isMainPage && (
        <>
          <div className="p-6 flex justify-center flex-col text-center">
            <p>Search for cars using natural language. Your imagination is the limit. </p>
            <SearchBox onSubmit={handleSearchSubmit} />
          </div>
        </>
      )}
      {showReset && (
        <div className="flex justify-center mt-4">
          <button 
            className="bg-red-500 hover:bg-red-700 text-white font-bold py-2 px-4 rounded"
            onClick={handleReset}
          >
            Reset
          </button>
        </div>
      )}
      <div className="p-6 flex justify-center">
        {searchLoading ? (
          <>
            <div className="pr-2">
              <LoadingSpinner/>
            </div>
            <p>Loading search results...</p>
          </>
        ) : (
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-x-4 gap-y-6 justify-items-center">
            {cars.map((car) => (
              <CarListing
                key={car.id}
                carListingId={car.id}
                title={car.title}
                brand={car.brand}
                model={car.model}
                kilometers={car.kilometers}
                engineCapacity={car.engineCapacity}
                fuelType={car.fuelType}
                year={car.year}
                price={car.price}
                city={car.city}
                horsepower={car.horsepower}
                isEditable={!isMainPage}
                onDelete={(deletedCarId) => setCarListings(cars.filter((car) => car.id !== deletedCarId))}
              />
            ))}
          </div>
        )}
      </div>
    </div>
  );
};

export default CarListings;
