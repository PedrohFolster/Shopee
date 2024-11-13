import React from 'react';
import PropTypes from 'prop-types';
import Input from '../../Components/Input/Input';
import Button from '../../Components/Button/Button';

const RegisterFormStep2 = ({ formData, handleChange, handleCepChange, handlePreviousStep, handleNextStep, errorMessage }) => {
  return (
    <>
      {errorMessage && <div className="error-message">{errorMessage}</div>}
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
      <button className="back-button" onClick={handlePreviousStep}>
        &#8592; Voltar
      </button>
    </>
  );
};

RegisterFormStep2.propTypes = {
  formData: PropTypes.object.isRequired,
  handleChange: PropTypes.func.isRequired,
  handleCepChange: PropTypes.func.isRequired,
  handlePreviousStep: PropTypes.func.isRequired,
  handleNextStep: PropTypes.func.isRequired,
  errorMessage: PropTypes.string,
};

export default RegisterFormStep2; 