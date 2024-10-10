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

  const logout = () => {
    setIsAuthenticated(false);
    localStorage.removeItem('isAuthenticated');
    localStorage.removeItem('sessionId');
    console.log('Sessão encerrada');
  };

  return (
    <AuthContext.Provider value={{ isAuthenticated, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};