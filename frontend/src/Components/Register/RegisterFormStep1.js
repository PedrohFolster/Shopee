import React from 'react';
import PropTypes from 'prop-types';
import Input from '../../Components/Input/Input';
import Button from '../../Components/Button/Button';
import { validarCpf } from '../../Util/CpfFormatter';

const RegisterFormStep1 = ({ formData, handleChange, handleNextStep, errorMessage }) => {
  return (
    <>
      {errorMessage && <div className="error-message">{errorMessage}</div>}
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
  );
};

RegisterFormStep1.propTypes = {
  formData: PropTypes.object.isRequired,
  handleChange: PropTypes.func.isRequired,
  handleNextStep: PropTypes.func.isRequired,
  errorMessage: PropTypes.string,
};

export default RegisterFormStep1; 