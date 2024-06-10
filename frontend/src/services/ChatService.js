import axios from 'axios';

const API_URL = 'http://localhost:8080/chat';

const sendUserPrompt = async (userPrompt, requestType) => {
  try {
    const response = await axios.post(API_URL, { userPrompt: userPrompt, requestType: requestType });
    return response.data;
  } catch (error) {
    throw error;
  }
};

export default {
  sendUserPrompt,
};




