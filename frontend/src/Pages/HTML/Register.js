import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import axios from 'axios';
import RegisterHeader from '../../Components/Register/RegisterHeader';
import RegisterFormStep1 from '../../Components/Register/RegisterFormStep1';
import RegisterFormStep2 from '../../Components/Register/RegisterFormStep2';
import '../CSS/Register.css';
import { formatarCpf, validarCpf } from '../../Util/CpfFormatter';
import { fetchAddressByCep } from '../../Util/CepAPI';
import { formatarTelefone } from '../../Util/TelefoneFormatter';
import { toast } from 'react-toastify';

const Register = () => {
  const [formData, setFormData] = useState({
    nomeCompleto: '',
    cpf: '',
    dataNascimento: '',
    email: '',
    telefone: '',
    senha: '',
    confirmarSenha: '',
    cep: '',
    rua: '',
    numero: '',
    cidade: '',
    estado: '',
    pais: '',
    complemento: ''
  });

  const [step, setStep] = useState(1);
  const [errorMessage, setErrorMessage] = useState('');
  const navigate = useNavigate();

  const handleChange = (e) => {
    const { name, value } = e.target;
    if (name === 'cpf') {
      if (value.length <= 14) {
        setFormData({ ...formData, [name]: formatarCpf(value) });
      }
    } else if (name === 'telefone') {
      if (value.replace(/\D/g, '').length <= 11) {
        setFormData({ ...formData, [name]: formatarTelefone(value) });
      }
    } else {
      setFormData({ ...formData, [name]: value });
    }
  };

  const handleNextStep = async () => {
    setErrorMessage(''); 
    if (step === 1) {
      const { nomeCompleto, cpf, dataNascimento, email, telefone, senha, confirmarSenha } = formData;
      if (!nomeCompleto || !cpf || !dataNascimento || !email || !telefone || !senha || !confirmarSenha) {
        toast.error('Por favor, preencha todos os campos obrigatórios.');
        return;
      }
      if (nomeCompleto.split(' ').length < 2) {
        toast.error('Nome completo deve conter pelo menos dois nomes.');
        return;
      }
      if (!validarCpf(cpf)) {
        toast.error('CPF inválido');
        return;
      }
      if (!email.includes('@')) {
        toast.error('E-mail inválido');
        return;
      }
      if (telefone.replace(/\D/g, '').length !== 11) {
        toast.error('Telefone deve conter 11 dígitos.');
        return;
      }
      if (senha !== confirmarSenha) {
        toast.error('As senhas não coincidem');
        return;
      }
      if (!senha.match(/^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/)) {
        toast.error('Senha deve conter pelo menos uma letra maiúscula, letras, números e um caractere especial.');
        return;
      }

      try {
        const response = await axios.get('http://localhost:8080/usuarios/verificar', {
          params: { email, cpf }
        });
        const { emailExiste, cpfExiste } = response.data;
        if (emailExiste) {
          toast.error('E-mail já está em uso.');
          return;
        }
        if (cpfExiste) {
          toast.error('CPF já está em uso.');
          return;
        }
      } catch (error) {
        toast.error('Erro ao verificar e-mail e CPF.');
        return;
      }

      setStep(2);
    } else if (step === 2) {
      const { cep, rua, numero, cidade, estado, pais } = formData;
      if (!cep || !rua || !numero || !cidade || !estado || !pais) {
        toast.error('Por favor, preencha todos os campos obrigatórios.');
        return;
      }
      if (cep.length !== 9) {
        toast.error('CEP inválido');
        return;
      }
      handleSubmit();
    }
  };

  const handlePreviousStep = () => {
    setStep(1);
  };

  const handleSubmit = async () => {
    try {
      const telefoneSemFormatacao = formData.telefone.replace(/\D/g, '');
      const cpfSemFormatacao = formData.cpf.replace(/\D/g, '');
      const dataNascimentoFormatada = formData.dataNascimento.split('T')[0];
  
      const response = await axios.post('http://localhost:8080/usuarios', {
        nome: formData.nomeCompleto,
        email: formData.email,
        telefone: telefoneSemFormatacao,
        dataNascimento: dataNascimentoFormatada,
        cpf: cpfSemFormatacao,
        enderecoDTO: {
          cep: formData.cep.replace('-', ''),
          rua: formData.rua,
          numero: formData.numero,
          cidade: formData.cidade,
          estado: formData.estado,
          pais: formData.pais,
          complemento: formData.complemento
        },
        usuarioAutenticarDTO: {
          login: formData.email,
          password: formData.senha,
          perfil: 'CLIENTE' 
        }
      });
  
      if (response.status === 200) {
        toast.success('Usuário registrado com sucesso!');
        navigate('/login');
      }
    } catch (error) {
      if (error.response && error.response.data && error.response.data.message) {
        toast.error(error.response.data.message);
      } else {
        toast.error('Erro ao registrar usuário');
      }
    }
  };

  const handleCepChange = async (e) => {
    let cep = e.target.value.replace(/\D/g, '');
    if (cep.length <= 8) {
      const formattedCep = cep.replace(/(\d{5})(\d{3})/, '$1-$2');
      setFormData({ ...formData, cep: formattedCep });
    }

    if (cep.length === 8) {
      try {
        const address = await fetchAddressByCep(cep);
        setFormData((prevData) => ({
          ...prevData,
          rua: address.rua || '',
          cidade: address.cidade || '',
          estado: address.estado || '',
          pais: address.pais || 'Brasil'
        }));
      } catch (error) {
        toast.error('Erro ao buscar endereço: ' + error.message);
      }
    }
  };

  return (
    <div className="register">
      <RegisterHeader />
      <main className="register-content">
        <h2>CRIAR CONTA</h2>
        <div className="separator"></div>
        <form onSubmit={(e) => e.preventDefault()}>
          {step === 1 && (
            <RegisterFormStep1 
              formData={formData} 
              handleChange={handleChange} 
              handleNextStep={handleNextStep} 
              errorMessage={errorMessage}
            />
          )}
          {step === 2 && (
            <RegisterFormStep2 
              formData={formData} 
              handleChange={handleChange} 
              handleCepChange={handleCepChange} 
              handlePreviousStep={handlePreviousStep} 
              handleNextStep={handleNextStep} 
              errorMessage={errorMessage}
            />
          )}
        </form>
        <Link to="/login" className="login-link">Já tem uma conta? Faça login</Link>
      </main>
    </div>
  );
};

export default Register;