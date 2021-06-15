import axios from 'axios';

// dodali smo putanju za heroku
const client = axios.create({
  baseURL: 'https://mrs-isa-pharmacy.herokuapp.com',
});

client.interceptors.request.use(
    (config) => {
      let token = localStorage.getItem('USER_TOKEN');
  
      if (token) {
        config.headers['Authorization'] = `Bearer ${ token }`;
      }
  
      return config;
    }, 
  
    (error) => {
      return Promise.reject(error);
    }
);

export {client}