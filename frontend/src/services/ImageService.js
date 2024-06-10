import axios from 'axios';

const API_URL = 'http://localhost:8080/images';

const getImagesForCarListing = async (carListingId) => {
  try {
    const response = await axios.get(`${API_URL}/${carListingId}`);
    return response.data;
  } catch (error) {
    throw error;
  }
};

const getFirstImageForCarListing = async (carListingId) => {
  try {
    const response = await axios.get(`${API_URL}/get-first/${carListingId}`);
    return response.data;
  } catch (error) {
    throw error;
  }
};

export default {
  getImagesForCarListing,
  getFirstImageForCarListing,
};
