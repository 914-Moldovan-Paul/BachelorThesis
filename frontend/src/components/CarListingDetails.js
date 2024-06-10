import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import CarService from '../services/CarService';
import ImageService from '../services/ImageService';

const CarListingDetails = () => {
  const { carListingId } = useParams();
  const [carListing, setCarListing] = useState(null);
  const [images, setImages] = useState([]);
  const [currentImageIndex, setCurrentImageIndex] = useState(0);
  const [showPhoneNumber, setShowPhoneNumber] = useState(false);
  const [showFullDescription, setShowFullDescription] = useState(false);

  useEffect(() => {
    const fetchCarListing = async () => {
      try {
        const data = await CarService.getCarListingById(carListingId);
        setCarListing(data);
      } catch (error) {
        console.error('Error fetching car listing:', error);
      }
    };

    const fetchImages = async () => {
      try {
        const data = await ImageService.getImagesForCarListing(carListingId);
        setImages(data);
      } catch (error) {
        console.error('Error fetching images:', error);
      }
    };

    fetchCarListing();
    fetchImages();
  }, [carListingId]);

  const handlePreviousImage = () => {
    setCurrentImageIndex((prevIndex) => (prevIndex === 0 ? images.length - 1 : prevIndex - 1));
  };

  const handleNextImage = () => {
    setCurrentImageIndex((prevIndex) => (prevIndex === images.length - 1 ? 0 : prevIndex + 1));
  };

  const handleContactSeller = () => {
    setShowPhoneNumber(true);
  };

  const toggleFullDescription = () => {
    setShowFullDescription(!showFullDescription);
  };

  if (!carListing) {
    return <div>Loading...</div>;
  }

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 mt-6">
      <div className="flex flex-col md:flex-row -mx-4">
        <div className="md:flex-1 px-4">
          <div>
            <div className="relative h-64 md:h-80 rounded-lg bg-gray-100 mb-4">
              <div className="absolute inset-0 flex items-center justify-center">
                {images.length > 0 && (
                  <img
                    src={`data:image/jpeg;base64,${images[currentImageIndex]}`}
                    alt={`Car Image ${currentImageIndex + 1}`}
                    className="h-full w-full object-cover rounded-lg"
                  />
                )}
              </div>
              {images.length > 1 && (
                <>
                  <button
                    onClick={handlePreviousImage}
                    className="absolute left-0 top-1/2 transform -translate-y-1/2 bg-gray-200 hover:bg-gray-300 rounded-full p-2 ml-2 focus:outline-none"
                  >
                    &#9664;
                  </button>
                  <button
                    onClick={handleNextImage}
                    className="absolute right-0 top-1/2 transform -translate-y-1/2 bg-gray-200 hover:bg-gray-300 rounded-full p-2 mr-2 focus:outline-none"
                  >
                    &#9654;
                  </button>
                </>
              )}
            </div>

            {images.length > 1 && (
              <div className="flex justify-center -mx-2 mb-4">
                {images.map((image, index) => (
                  <div key={index} className="flex-1 px-2">
                    <button
                      onClick={() => setCurrentImageIndex(index)}
                      className={`focus:outline-none w-full rounded-lg h-24 md:h-32 bg-gray-100 flex items-center justify-center ${currentImageIndex === index ? 'ring-2 ring-blue-600 ring-inset' : ''}`}
                    >
                      <img
                        src={`data:image/jpeg;base64,${image}`}
                        alt={`Thumbnail ${index + 1}`}
                        className="h-full w-full object-cover rounded-lg"
                      />
                    </button>
                  </div>
                ))}
              </div>
            )}
          </div>
        </div>
        <div className="md:flex-1 px-4">
          <h2 className="mb-2 leading-tight tracking-tight font-bold text-gray-800 text-2xl md:text-3xl">{carListing.title}</h2>
          <p className="text-gray-500 text-sm">By {carListing.userDisplayName}</p>

          <div className="flex items-center space-x-4 my-4">
            <div>
              <div className="rounded-lg bg-gray-100 flex py-2 px-3">
                <span className="font-bold text-blue-600 text-3xl">{carListing.price} EUR</span>
              </div>
            </div>
            <div>
              {showPhoneNumber ? (
                <div className="h-14 px-6 py-2 font-semibold rounded-xl bg-blue-100 text-blue-600 flex items-center justify-center">
                  {carListing.phoneNumber}
                </div>
              ) : (
                <button
                  type="button"
                  onClick={handleContactSeller}
                  className="h-14 px-6 py-2 font-semibold rounded-xl bg-blue-600 hover:bg-blue-700 text-white"
                >
                  Contact Seller
                </button>
              )}
            </div>
          </div>

          <p className="text-gray-500">
            {carListing.description.length > 70 ? (
              <>
                {carListing.description.substring(0, 70)}...
                <button onClick={toggleFullDescription} className="text-blue-600 hover:underline">
                  Read More
                </button>
              </>
            ) : (
              carListing.description
            )}
          </p>

          {showFullDescription && (
            <div className="fixed inset-0 flex items-center justify-center bg-gray-900 bg-opacity-50 z-50">
              <div className="bg-white rounded-lg p-4 max-w-lg w-full relative">
                <button
                  onClick={toggleFullDescription}
                  className="absolute top-2 right-2 text-gray-600 hover:text-gray-900"
                >
                  &times;
                </button>
                <h3 className="text-xl font-bold mb-2">Description</h3>
                <p className="text-gray-700">{carListing.description}</p>
              </div>
            </div>
          )}

          <div className="grid grid-cols-1 md:grid-cols-2 gap-4 mt-4 text-gray-700">
            <div><strong>Brand:</strong> {carListing.brand}</div>
            <div><strong>Model:</strong> {carListing.model}</div>
            <div><strong>Year:</strong> {carListing.year}</div>
            <div><strong>Generation:</strong> {carListing.generation}</div>
            <div><strong>VIN:</strong> {carListing.vehicleIdentificationNumber}</div>
            <div><strong>Kilometers:</strong> {carListing.kilometers}</div>
            <div><strong>Horsepower:</strong> {carListing.horsepower} HP</div>
            <div><strong>Engine Capacity:</strong> {carListing.engineCapacity} cc</div>
            <div><strong>Transmission:</strong> {carListing.transmission}</div>
            <div><strong>Gearbox Type:</strong> {carListing.gearboxType}</div>
            <div><strong>Fuel Type:</strong> {carListing.fuelType}</div>
            <div><strong>Color:</strong> {carListing.color}</div>
            <div><strong>City:</strong> {carListing.city}</div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default CarListingDetails;
