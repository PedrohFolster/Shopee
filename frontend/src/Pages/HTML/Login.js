import React, { useContext } from 'react';
import { Link } from 'react-router-dom';
import '../CSS/Login.css';
import { AuthContext } from '../../Util/Authentication';
import LoginHeader from '../../Components/Login/LoginHeader';
import LoginForm from '../../Components/Login/LoginForm';

const Login = () => {
  const { login } = useContext(AuthContext);

  return (
    <div className="login">
      <LoginHeader />
      <main className="login-content centered-form">
        <h2>LOGIN</h2>
        <LoginForm login={login} />
        <Link to="/register" className="register-link">Não tem uma conta? Registre-se</Link>
      </main>
    </div>
  );
};

export default Login;