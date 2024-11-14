// api.js ou axiosConfig.js
import axios from 'axios';

// Cria uma instância do Axios
const api = axios.create({
  baseURL: 'http://localhost:8080', // Altere para a URL do seu backend
});

// Intercepta as requisições para adicionar o token de autorização
api.interceptors.request.use(
  (config) => {
    // Obtém o token do localStorage
    const token = localStorage.getItem('token'); // ou sessionStorage.getItem('token')

    // Se o token existir, adiciona ao cabeçalho Authorization
    if (token) {
      config.headers['Authorization'] = `Bearer ${token}`;
    }

    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

export default api;
