import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import axios from 'axios';
import Header from '../../Components/Menu/Items/Header/Header';
import Input from '../../Components/Input/Input';
import Button from '../../Components/Button/Button';
import '../CSS/Register.css';
import { formatarCpf, validarCpf } from '../../Util/CpfFormatter';
import { fetchAddressByCep } from '../../Util/CepAPI';
import { formatarTelefone } from '../../Util/TelefoneFormatter';

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

  const handleNextStep = () => {
    setErrorMessage(''); 
    if (step === 1) {
      const { nomeCompleto, cpf, dataNascimento, email, telefone, senha, confirmarSenha } = formData;
      if (!nomeCompleto || !cpf || !dataNascimento || !email || !telefone || !senha || !confirmarSenha) {
        setErrorMessage('Por favor, preencha todos os campos obrigatórios.');
        return;
      }
      if (nomeCompleto.split(' ').length < 2) {
        setErrorMessage('Nome completo deve conter pelo menos dois nomes.');
        return;
      }
      if (!validarCpf(cpf)) {
        setErrorMessage('CPF inválido');
        return;
      }
      if (!email.includes('@')) {
        setErrorMessage('E-mail inválido');
        return;
      }
      if (senha !== confirmarSenha) {
        setErrorMessage('As senhas não coincidem');
        return;
      }
      if (!senha.match(/^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/)) {
        setErrorMessage('Senha deve conter pelo menos uma letra maiúscula, letras, números e um caractere especial.');
        return;
      }
      setStep(2);
    } else if (step === 2) {
      const { cep, rua, numero, cidade, estado, pais } = formData;
      if (!cep || !rua || !numero || !cidade || !estado || !pais) {
        setErrorMessage('Por favor, preencha todos os campos obrigatórios.');
        return;
      }
      if (cep.length !== 9) {
        setErrorMessage('CEP inválido');
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
          username: formData.email,
          passwordHash: formData.senha
        }
      });

      if (response.status === 200) {
        alert('Usuário registrado com sucesso!');
        navigate('/login');
      }
    } catch (error) {
      if (error.response && error.response.data) {
        setErrorMessage(error.response.data);
      } else {
        setErrorMessage('Erro ao registrar usuário');
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
        setErrorMessage('Erro ao buscar endereço: ' + error.message);
      }
    }
  };

  return (
    <div className="register">
      <Header searchHidden={true} navbarHidden={true}/>
      <main className="register-content">
        {step === 2 && (
          <button className="back-button" onClick={handlePreviousStep}>
            &#8592; Voltar
          </button>
        )}
        <h2>CRIAR CONTA</h2>
        <div className="separator"></div>
        {errorMessage && <div className="error-message">{errorMessage}</div>}
        <form onSubmit={(e) => e.preventDefault()}>
          {step === 1 && (
            <>
              <div className="form-row completo">
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
              <div className="form-row-register">
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
              <div className="form-row-register">
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
              <div className="form-row-register">
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
              <Button type="button-register" onClick={handleNextStep}>Próxima Etapa</Button>
            </>
          )}
          {step === 2 && (
            <>
              <div className="form-row-register">
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
              <div className="form-row-register">
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
              <div className="form-row-register">
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
              <div className="form-row completo">
                <Input
                  type="text"
                  id="complemento"
                  name="complemento"
                  value={formData.complemento}
                  onChange={handleChange}
                  placeholder="Complemento"
                />
              </div>
              <Button type="button-register" onClick={handleNextStep}>Finalizar Cadastro</Button>
            </>
          )}
        </form>
        <Link to="/login" className="login-link">Já tem uma conta? Faça login</Link>
      </main>
    </div>
  );
};

export default Register;