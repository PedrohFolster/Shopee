import axios from 'axios';

export const fetchAddressByCep = async (cep) => {
  try {
    const response = await axios.get(`https://viacep.com.br/ws/${cep}/json/`);
    if (response.data.erro) {
      throw new Error('CEP não encontrado');
    }
    return {
      rua: response.data.logradouro,
      cidade: response.data.localidade,
      estado: response.data.uf,
      pais: 'Brasil'
    };
  } catch (error) {
    console.error('Erro ao buscar endereço:', error);
    throw error;
  }
};