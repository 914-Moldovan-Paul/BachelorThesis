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

const getCarListingsForQuery = async (query) => {
  try {
    const response = await axios.post(API_URL + "/by-query", query);
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

export default {
  getAllCarListings,
  getCarListingsForQuery,
  createCarListing,
};
