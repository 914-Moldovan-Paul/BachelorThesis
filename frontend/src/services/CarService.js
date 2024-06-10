import axios from 'axios';

const API_URL = 'http://localhost:8080/cars';

const getAllCarListings = async () => {
  try {
    const response = await axios.get(API_URL);
    return response.data;
  } catch (error) {
    throw error;
  }
};

const getCarListingById = async (carId) => {
  try {
    const response = await axios.get(API_URL + `/${carId}`);
    return response.data;
  } catch (error) {
    throw error;
  }

}

const getCarListingsForQuery = async (queryDto) => {
  try {
    const response = await axios.post(API_URL + "/by-query", queryDto);
    return response.data;
  } catch (error) {
    throw error;
  }
};

const createCarListing = async (carListingData) => {
  try {
    const response = await axios.post(API_URL, carListingData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });
    return response.data;
  } catch (error) {
    throw error;
  }
};

const updateCarListing = async (carId, updatedCarListingData) => {
  try {
    const response = await axios.put(API_URL + `/${carId}`, updatedCarListingData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });
    return response.data;
  } catch (error) {
    throw error;
  }
};

const deleteCarListing = async (carId) => {
  try {
    const response = await axios.delete(API_URL + `/${carId}`);
    return response.data;
  } catch (error) {
    throw error;
  }
};

export default {
  getAllCarListings,
  getCarListingById,
  getCarListingsForQuery,
  createCarListing,
  updateCarListing,
  deleteCarListing,
};
