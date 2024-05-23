import React, { useEffect, useState } from 'react';
import Car from './Car';
import CarService from '../services/CarService';
import SearchBox from './SearchBox';
import axios from 'axios';

const Cars = () => {
  const [cars, setCars] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const fetchCars = async () => {
    setLoading(true);
    try {
      const data = await CarService.getAllCarListings();
      setCars(data); 
      console.log(data);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchCars();
  }, []);

  const handleSearchSubmit = async (userPrompt) => {
    try {
      const response = await axios.post('/chat', { userPrompt });

      const selectedCars = await CarService.getCarListingsForQuery(response.data)
      setCars(selectedCars.data)

    } catch (error) {
      console.error('Error handling search submit:', error);
    }
  };

  if (loading) {
    return <p>Loading...</p>;
  }

  if (error) {
    return <p>Error: {error}</p>;
  }

  return (
    <div className="cars-container">
      <div className="p-6 flex justify-center">
        <SearchBox onSubmit={handleSearchSubmit} />
      </div>
      <div className="p-6 flex justify-center">
        <div className="grid grid-cols-1 md:grid-cols-2 gap-x-4 gap-y-6 justify-items-center">
          {cars.map((car) => (
            <Car
              key={car.id} // Assuming each car has a unique id
              photo={car.photoUrl}
              title={car.title}
              carModel={car.carModel}
              mileage={car.mileage}
              engineSize={car.engineSize}
              fuelType={car.fuelType}
              year={car.year}
              price={car.price}
            />
          ))}
        </div>
      </div>
    </div>
  );
};

export default Cars;
