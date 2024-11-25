import React from 'react';
import { Navigate } from 'react-router-dom';

const isTokenValid = (token) => {
  if (!token) return false;
  try {
    const payload = JSON.parse(atob(token.split('.')[1]));
    return payload.exp * 1000 > Date.now();
  } catch (e) {
    return false;
  }
};

const ProtectedRoute = ({ element: Component }) => {
  const token = localStorage.getItem('token');
  return isTokenValid(token) ? Component : <Navigate to="/login" />;
};

export default ProtectedRoute; 