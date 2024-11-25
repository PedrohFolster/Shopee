import axios from 'axios';

export const fetchAddressByCep = async (cep) => {
  try {
    const sanitizedCep = cep.replace('-', '');

    const response = await axios.get(`https://viacep.com.br/ws/${sanitizedCep}/json/`, {
      withCredentials: false
    });

    if (response.data.erro) {
      throw new Error('CEP não encontrado');
    }

    const formattedCep = sanitizedCep.replace(/(\d{5})(\d{3})/, '$1-$2');

    return {
      cep: formattedCep,
      rua: response.data.logradouro,
      cidade: response.data.localidade,
      estado: response.data.uf,
      pais: 'Brasil'
    };
  } catch (error) {
    console.error('Erro ao buscar endereço:', error);
    if (error.code === 'ERR_NETWORK') {
      throw new Error('Erro de rede. Por favor, verifique sua conexão.');
    }
    throw error;
  }
};