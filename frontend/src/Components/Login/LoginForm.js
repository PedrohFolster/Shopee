import React, { useState } from 'react';
import PropTypes from 'prop-types';
import Input from '../../Components/Input/Input';
import Button from '../../Components/Button/Button';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import { toast } from 'react-toastify';

const LoginForm = ({ login }) => {
  const [formData, setFormData] = useState({
    email: '',
    senha: ''
  });

  const navigate = useNavigate();

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = async () => {
    try {
      const response = await axios.post(`${process.env.REACT_APP_API_URL}/login`, {
        login: formData.email,
        password: formData.senha
      }, { withCredentials: true });

      if (response.status === 200 && response.data.userId) {
        const token = response.data.token;

        console.log('Token:', token);

        localStorage.setItem('token', token);

        document.cookie = `token=${token}; path=/; secure; samesite=strict`;

        toast.success('Login realizado com sucesso!'); 

        login();
        navigate('/home');
      } else {
        toast.error('Usuário ou senha inválidos');
      }
    } catch (error) {
      console.error('Erro:', error);
      toast.error('Usuário ou senha inválidos');
    }
  };

  return (
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
  );
};

LoginForm.propTypes = {
  login: PropTypes.func.isRequired,
};

export default LoginForm;