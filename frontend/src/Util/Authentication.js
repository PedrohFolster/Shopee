import React, { createContext, useState, useEffect } from 'react';
import axios from 'axios';
import { toast } from 'react-toastify'; // Importação do toast

export const AuthContext = createContext();

export const isTokenValid = (token) => {
  if (!token) return false;
  try {
    const payload = JSON.parse(atob(token.split('.')[1]));
    return payload.exp * 1000 > Date.now();
  } catch (e) {
    return false;
  }
};

export const AuthProvider = ({ children }) => {
  const [isAuthenticated, setIsAuthenticated] = useState(() => {
    const token = localStorage.getItem('token');
    return isTokenValid(token);
  });

  useEffect(() => {
    const validateSession = async () => {
      const token = localStorage.getItem('token');
      if (isTokenValid(token)) {
        try {
          const response = await axios.get(`${process.env.REACT_APP_API_URL}/validate-session`, {
            headers: { 'Authorization': `Bearer ${token}` }
          });
          if (response.status === 200) {
            setIsAuthenticated(true);
            console.log('Sessão válida');
          } else {
            handleSessionInvalid();
          }
        } catch (error) {
          console.error('Erro ao validar sessão:', error);
          handleSessionInvalid();
        }
      } else {
        handleSessionInvalid();
      }
    };

    const handleSessionInvalid = () => {
      localStorage.removeItem('token');
      setCookie('token', '', -1);
      setIsAuthenticated(false);
      console.log('Sessão inválida ou expirada');
    };

    validateSession();

    const requestInterceptor = axios.interceptors.request.use(
      (config) => {
        const token = localStorage.getItem('token');
        if (token) {
          config.headers['Authorization'] = `Bearer ${token}`;
        }
        return config;
      },
      (error) => {
        return Promise.reject(error);
      }
    );

    return () => {
      axios.interceptors.request.eject(requestInterceptor);
    };
  }, []);

  const login = () => {
    setIsAuthenticated(true);
    console.log('Sessão válida');
  };

  const logout = () => {
    localStorage.removeItem('token');
    setCookie('token', '', -1); 
    setIsAuthenticated(false);
    toast.success('Logout realizado com sucesso!');
    console.log('Logout bem-sucedido');
  };

  const setCookie = (name, value, days) => {
    const expires = new Date(Date.now() + days * 864e5).toUTCString();
    document.cookie = name + '=' + encodeURIComponent(value) + '; expires=' + expires + '; path=/';
  };

  return (
    <AuthContext.Provider value={{ isAuthenticated, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};