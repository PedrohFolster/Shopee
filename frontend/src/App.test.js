import { render, screen } from '@testing-library/react';
import { act } from 'react';
import App from './App';
import { AuthContext } from './Util/Authentication'; // Certifique-se de que o caminho está correto

test('renders filter products text', () => {
  const authContextValue = { isAuthenticated: true }; // Defina o valor do contexto conforme necessário

  act(() => {
    render(
      <AuthContext.Provider value={authContextValue}>
        <App />
      </AuthContext.Provider>
    );
  });

  const linkElement = screen.getByText(/Filtrar Produtos/i);
  expect(linkElement).toBeInTheDocument();
});