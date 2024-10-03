import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import axios from 'axios';
import Header from '../../Components/Menu/Items/Header/Header';
import Input from '../../Components/Input/Input';
import Button from '../../Components/Button/Button';
import '../CSS/Register.css';
import { formatarCpf, validarCpf } from '../../Util/CpfFormatter';
import { fetchAddressByCep } from '../../Util/CepAPI';

const Register = () => {
  const [formData, setFormData] = useState({
    nomeCompleto: '',
    cpf: '',
    dataNascimento: '',
    email: '', // Este será o username
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
  const navigate = useNavigate();

  const handleChange = (e) => {
    const { name, value } = e.target;
    if (name === 'cpf') {
      if (value.length <= 14) { // Limita a 14 caracteres para o formato "000.000.000-00"
        setFormData({ ...formData, [name]: formatarCpf(value) });
      }
    } else {
      setFormData({ ...formData, [name]: value });
    }
  };

  const handleNextStep = () => {
    if (step === 1) {
      const { nomeCompleto, cpf, dataNascimento, email, telefone, senha, confirmarSenha } = formData;
      if (!nomeCompleto || !cpf || !dataNascimento || !email || !telefone || !senha || !confirmarSenha) {
        alert('Por favor, preencha todos os campos obrigatórios.');
        return;
      }
      if (!validarCpf(cpf)) {
        alert('CPF inválido');
        return;
      }
      if (!email.includes('@')) {
        alert('E-mail inválido');
        return;
      }
      if (senha !== confirmarSenha) {
        alert('As senhas não coincidem');
        return;
      }
      setStep(2);
    } else if (step === 2) {
      const { cep, rua, numero, cidade, estado, pais } = formData;
      if (!cep || !rua || !numero || !cidade || !estado || !pais) {
        alert('Por favor, preencha todos os campos obrigatórios.');
        return;
      }
      if (cep.length !== 9) {
        alert('CEP inválido');
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
      const cepSemHifen = formData.cep.replace('-', ''); // Remove o hífen para enviar à API
      const response = await axios.post('http://localhost:8080/usuarios', {
        nome: formData.nomeCompleto,
        email: formData.email, // Usar email como username
        senha: formData.senha,
        cpf: formData.cpf,
        dataNascimento: formData.dataNascimento,
        telefone: formData.telefone,
        enderecoDTO: {
          cep: cepSemHifen,
          rua: formData.rua,
          numero: formData.numero,
          cidade: formData.cidade,
          estado: formData.estado,
          pais: formData.pais,
          complemento: formData.complemento
        }
      });
      if (response.status === 200) {
        alert('Usuário registrado com sucesso!');
        navigate('/login');
      } else {
        alert('Erro ao registrar usuário');
      }
    } catch (error) {
      console.error('Erro:', error);
      alert('Erro ao registrar usuário');
    }
  };

  const fetchAddress = async (cep) => {
    try {
      const response = await axios.get(`https://viacep.com.br/ws/${cep}/json/`);
      if (!response.data.erro) {
        setFormData((prevData) => ({
          ...prevData,
          rua: response.data.logradouro || '',
          cidade: response.data.localidade || '',
          estado: response.data.uf || '',
          pais: 'Brasil'
        }));
      }
    } catch (error) {
      console.error('Erro ao buscar endereço:', error);
    }
  };

  const handleCepChange = async (e) => {
    let cep = e.target.value.replace(/\D/g, ''); // Remove caracteres não numéricos
    if (cep.length <= 8) { // Limita a 8 caracteres numéricos
      const formattedCep = cep.replace(/(\d{5})(\d{3})/, '$1-$2'); // Formata o CEP com hífen
      setFormData({ ...formData, cep: formattedCep });
    }

    if (cep.length === 8) { // Verifica se o CEP tem 8 dígitos numéricos
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
        alert('Erro ao buscar endereço: ' + error.message);
      }
    }
  };

  return (
    <div className="register">
      <Header />
      <main className="register-content">
        {step === 2 && (
          <button className="back-button" onClick={handlePreviousStep}>
            &#8592; Voltar
          </button>
        )}
        <h2>CRIAR CONTA</h2>
        <div className="separator">ou</div>
        <form onSubmit={(e) => e.preventDefault()}>
          {step === 1 && (
            <>
              <div className="form-row full-width">
                <Input
                  type="text"
                  id="nomeCompleto"
                  name="nomeCompleto"
                  value={formData.nomeCompleto}
                  onChange={handleChange}
                  placeholder="Nome completo*"
                  required
                />
              </div>
              <div className="form-row">
                <Input
                  type="text"
                  id="cpf"
                  name="cpf"
                  value={formData.cpf}
                  onChange={handleChange}
                  placeholder="CPF*"
                  required
                  className={!validarCpf(formData.cpf) ? 'input-error' : ''}
                />
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
                  type="date"
                  id="dataNascimento"
                  name="dataNascimento"
                  value={formData.dataNascimento}
                  onChange={handleChange}
                  placeholder="Data de nascimento*"
                  required
                />
                <Input
                  type="tel"
                  id="telefone"
                  name="telefone"
                  value={formData.telefone}
                  onChange={handleChange}
                  placeholder="Telefone*"
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
                  placeholder="Crie sua senha*"
                  required
                />
                <Input
                  type="password"
                  id="confirmarSenha"
                  name="confirmarSenha"
                  value={formData.confirmarSenha}
                  onChange={handleChange}
                  placeholder="Confirme sua senha*"
                  required
                />
              </div>
              <Button type="button" onClick={handleNextStep}>Próxima Etapa</Button>
            </>
          )}
          {step === 2 && (
            <>
              <div className="form-row">
                <Input
                  type="text"
                  id="cep"
                  name="cep"
                  value={formData.cep}
                  onChange={handleCepChange}
                  placeholder="CEP*"
                  required
                />
                <Input
                  type="text"
                  id="cidade"
                  name="cidade"
                  value={formData.cidade}
                  onChange={handleChange}
                  placeholder="Cidade*"
                  required
                />
              </div>
              <div className="form-row">
                <Input
                  type="text"
                  id="rua"
                  name="rua"
                  value={formData.rua}
                  onChange={handleChange}
                  placeholder="Rua*"
                  required
                />
                <Input
                  type="text"
                  id="numero"
                  name="numero"
                  value={formData.numero}
                  onChange={handleChange}
                  placeholder="Número*"
                  required
                />
              </div>
              <div className="form-row">
                <Input
                  type="text"
                  id="estado"
                  name="estado"
                  value={formData.estado}
                  onChange={handleChange}
                  placeholder="Estado*"
                  required
                />
                <Input
                  type="text"
                  id="pais"
                  name="pais"
                  value={formData.pais}
                  onChange={handleChange}
                  placeholder="País*"
                  required
                />
              </div>
              <div className="form-row full-width">
                <Input
                  type="text"
                  id="complemento"
                  name="complemento"
                  value={formData.complemento}
                  onChange={handleChange}
                  placeholder="Complemento"
                />
              </div>
              <Button type="button" onClick={handleNextStep}>Criar conta</Button>
            </>
          )}
        </form>
        <Link to="/login" className="login-link">Já tem uma conta? Faça login</Link>
      </main>
    </div>
  );
};

export default Register;