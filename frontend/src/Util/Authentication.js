import React, { createContext, useState, useEffect } from 'react';
import axios from 'axios';

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [isAuthenticated, setIsAuthenticated] = useState(() => {
    return localStorage.getItem('isAuthenticated') === 'true';
  });

  useEffect(() => {
    const validateSession = async () => {
      const sessionId = localStorage.getItem('sessionId');
      if (sessionId) {
        try {
          const response = await axios.get('http://localhost:8080/validate-session', {
            headers: { 'session-id': sessionId }
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
          console.log('Erro ao validar sessão');
        }
      }
    };

    validateSession();
  }, []);

  const login = () => {
    setIsAuthenticated(true);
    localStorage.setItem('isAuthenticated', 'true');
    console.log('Sessão válida');
  };

  const logout = async () => {
    const sessionId = localStorage.getItem('sessionId');
    localStorage.removeItem('isAuthenticated');
  
    try {
        await axios.post('http://localhost:8080/logout', {}, {
            headers: {
                'session-id': sessionId
            }
        });
        console.log('Logout bem-sucedido');
    } catch (error) {
        console.error('Erro ao realizar logout:', error);
    }
    localStorage.removeItem('sessionId'); // Remover o sessionId do armazenamento local
    window.location.reload(); // Recarregar a página
};

  const addAuthHeader = (config) => {
    const token = localStorage.getItem('token');
    if (token) {
      config.headers = {
        ...config.headers,
        'Authorization': `Bearer ${token}`
      };
    }
    return config;
  };

  return (
    <AuthContext.Provider value={{ isAuthenticated, login, logout, addAuthHeader }}>
      {children}
    </AuthContext.Provider>
  );
};
