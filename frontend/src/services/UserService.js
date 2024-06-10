import axios from 'axios';

const API_URL = 'http://localhost:8080/users';

const getAllCarListingsForUser = async (userId) => {
  try {
    const response = await axios.get(API_URL + `/${userId}/cars`);
    return response.data;
  } catch (error) {
    throw error;
  }
};



export default {
    getAllCarListingsForUser,
};
