import React, { createContext, useState, useEffect } from 'react';
import axios from 'axios';

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [isAuthenticated, setIsAuthenticated] = useState(false);

  useEffect(() => {
    const validateSession = async () => {
      const token = localStorage.getItem('token');
      if (token) {
        console.log(`Bearer ${token}`); // Imprime o token Bearer no console
        try {
          const response = await axios.get('http://localhost:8080/validate-session', {
            headers: { 'Authorization': `Bearer ${token}` },
            withCredentials: true
          });
          if (response.status === 200) {
            setIsAuthenticated(true);
            console.log('Sessão válida');
          } else {
            setIsAuthenticated(false);
            console.log('Sessão inválida');
          }
        } catch (error) {
          console.error('Erro ao validar sessão:', error);
          setIsAuthenticated(false);
        }
      } else {
        setIsAuthenticated(false);
      }
    };

    validateSession();
  }, []);

  useEffect(() => {
    const requestInterceptor = axios.interceptors.request.use(
      (config) => {
        const token = localStorage.getItem('token');
        if (token) {
          config.headers['Authorization'] = `Bearer ${token}`;
          console.log(`Bearer ${token}`);
        }
        config.withCredentials = true; // Define withCredentials como true por padrão
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

  const logout = async () => {
    const token = localStorage.getItem('token');
    
    try {
      await axios.post('http://localhost:8080/logout', {}, {
        headers: {
          'Authorization': `Bearer ${token}`
        },
        withCredentials: true
      });

      localStorage.removeItem('token');
      setIsAuthenticated(false);
      
      console.log('Logout bem-sucedido');
    } catch (error) {
      console.error('Erro ao realizar logout:', error);
      throw error; 
    }
  };

  return (
    <AuthContext.Provider value={{ isAuthenticated, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};