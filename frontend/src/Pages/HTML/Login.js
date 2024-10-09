import React, { useState, useContext } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import axios from 'axios';
import Header from '../../Components/Menu/Items/Header/Header';
import Input from '../../Components/Input/Input';
import Button from '../../Components/Button/Button';
import '../CSS/Login.css';
import { AuthContext } from '../../Util/Authentication';

const Login = () => {
  const [formData, setFormData] = useState({
    email: '',
    senha: ''
  });

  const navigate = useNavigate();
  const { login } = useContext(AuthContext);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = async () => {
    try {
      const response = await axios.post('http://localhost:8080/login', {
        username: formData.email,
        password: formData.senha
      }, { withCredentials: true });

      if (response.status === 200) {
        const sessionId = response.data.sessionId;
        console.log('Session ID:', sessionId);
        // Armazene o ID da sessão conforme necessário
        login(); // Atualize o contexto de autenticação
        navigate('/home');
      } else {
        alert('Usuário ou senha inválidos');
      }
    } catch (error) {
      console.error('Erro:', error);
      alert('Usuário ou senha inválidos');
    }
  };

  return (
    <div className="login">
      <Header searchHidden={true} />
      <main className="login-content centered-form">
        <h2>LOGIN</h2>
        <form onSubmit={(e) => e.preventDefault()}>
          <div className="form-row">
            <Input
              type="email"
              id="email"
              name="email"
              value={formData.email}
              onChange={handleChange}
              placeholder="E-mail*"
              required
            />
          </div>
          <div className="form-row">
            <Input
              type="password"
              id="senha"
              name="senha"
              value={formData.senha}
              onChange={handleChange}
              placeholder="Senha*"
              required
            />
          </div>
          <Button type="button-login" onClick={handleSubmit}>Entrar</Button>
        </form>
        <Link to="/register" className="register-link">Não tem uma conta? Registre-se</Link>
      </main>
    </div>
  );
};

export default Login;