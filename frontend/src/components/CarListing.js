import React, { useEffect, useState } from 'react';
import CarService from '../services/CarService';
import { useNavigate } from 'react-router';
import ImageService from '../services/ImageService';
import { ReactComponent as SpeedometerIcon } from '../assets/speedometer.svg';
import { ReactComponent as EngineIcon } from '../assets/engine.svg';
import { ReactComponent as FuelPumpIcon } from '../assets/fuelpump.svg';
import { ReactComponent as LocationIcon } from '../assets/location.svg';
import { ReactComponent as CalendarIcon } from '../assets/calendar.svg';
import { ReactComponent as HorseIcon } from '../assets/horse.svg';

const CarListing = ({ carListingId, title, brand, model, kilometers, engineCapacity, fuelType, year, price, city, horsepower, isEditable, onDelete }) => {
  const navigate = useNavigate();
  
  const [photo, setPhoto] = useState(null);

  useEffect(() => {
    const getFirstImage = async (carListingId) => {
      try {
        const response = await ImageService.getFirstImageForCarListing(carListingId);
        setPhoto(response);
      } catch (error) {
        console.error('Error fetching car image:', error);
      }
    };

    getFirstImage(carListingId);
  }, [carListingId]);

  const handleEdit = () => {
    navigate(`/edit-listing/${carListingId}`, { state: { isUpdate: true, carId: carListingId } });
  };

  const handleDelete = async () => {
    const confirmed = window.confirm('Are you sure you want to delete this listing?');
    if (confirmed) {
      try {
        await CarService.deleteCarListing(carListingId);
        onDelete(carListingId);
      } catch (error) {
        console.error('Error deleting car listing:', error);
      }
    }
  };

  const handleDetails = () => {
    navigate(`/car-listing-details/${carListingId}`);
  };

  return (
    <div
      className="max-w-xl mx-auto bg-white rounded-lg shadow-md overflow-hidden transform transition-transform hover:scale-105 cursor-pointer"
      onClick={handleDetails}
    >
      <div className="flex flex-col max-w-96">
        <div className="p-4">
          {photo ? (
            <img
              className="h-auto w-full rounded-lg aspect-[4/3] object-cover"
              src={`data:image/jpeg;base64,${photo}`}
              alt={model}
            />
          ) : (
            <p>Loading image...</p>
          )}
        </div>
        <div className="p-4">
          <h2 className="text-xl font-semibold text-gray-900">{title}</h2>
          <h2 className="text-l font-semibold text-gray-900">{brand} {model}</h2>
          <div className="mt-2 text-gray-600 flex flex-wrap gap-2">
            <div className="flex items-center">
              <SpeedometerIcon className="ml-1" width="20" height="20" />
              <span className="ml-2">{kilometers} km</span>
            </div>
            <div className="flex items-center">
              <EngineIcon className="ml-1" width="20" height="20" />
              <span className="ml-2">{engineCapacity} cc</span>
            </div>
            <div className="flex items-center">
              <FuelPumpIcon className="ml-1" width="20" height="20" />
              <span className="ml-2">{fuelType}</span>
            </div>
            <div className="flex items-center">
              <CalendarIcon className="ml-1" width="20" height="20" />
              <span className="ml-2">{year}</span>
            </div>
            <div className="flex items-center">
              <HorseIcon className="ml-1" width="20" height="20" />
              <span className="ml-2">{horsepower} hp</span>
            </div>
            <div className="flex items-center">
              <LocationIcon className="ml-1" width="20" height="20" />
              <span className="ml-2">{city}</span>
            </div>
          </div>
          <p className="mt-4 text-lg font-bold text-blue-600">{price} EUR</p>
          {isEditable && (
            <div className="flex justify-end mt-4" onClick={(e) => e.stopPropagation()}>
              <button className="mr-2 bg-blue-600 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded" onClick={handleEdit}>Edit</button>
              <button className="bg-red-500 hover:bg-red-700 text-white font-bold py-2 px-4 rounded" onClick={handleDelete}>Delete</button>
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default CarListing;
