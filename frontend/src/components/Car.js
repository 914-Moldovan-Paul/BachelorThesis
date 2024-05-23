import React from 'react';

const Car = ({ photo, title, carModel, mileage, engineSize, fuelType, year, price }) => {
  return (
    <div className="max-w-lg mx-auto bg-white rounded-lg shadow-md overflow-hidden">
      <div className="flex">
        <div className="w-5/12 p-4 flex justify-center items-center">
            <img className="h-full w-full object-cover" src={photo} alt={carModel} style={{ maxWidth: '100%', height: 'auto' }}/>
        </div>
        <div className="w-7/12 p-4">
          <h2 className="text-xl font-semibold text-gray-900">{title}</h2>
          <h2 className="text-l font-semibold tex   t-gray-900">{carModel}</h2>
          <div className="mt-2 text-gray-600">
            <p><strong>Mileage:</strong> {mileage}</p>
            <p><strong>Engine Size:</strong> {engineSize} L</p>
            <p><strong>Fuel Type:</strong> {fuelType}</p>
            <p><strong>Year:</strong> {year}</p>
          </div>
          <p className="mt-4 text-lg font-bold text-gray-900">${price}</p>
        </div>
      </div>
    </div>
  );
};

export default Car;
