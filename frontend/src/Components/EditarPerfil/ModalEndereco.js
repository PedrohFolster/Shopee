import React, { useEffect } from 'react';
import PropTypes from 'prop-types';
import Button from '../../Components/Button/Button';
import { fetchAddressByCep } from '../../Util/CepAPI';

const ModalEndereco = ({ isOpen, onClose, enderecoDTO, setEnderecoDTO, enderecoSenha, setEnderecoSenha, handleSubmit }) => {
    if (!isOpen) return null;

    const handleCepChange = async (e) => {
        let newCep = e.target.value.replace(/\D/g, '');
        if (newCep.length <= 8) {
            const formattedCep = newCep.replace(/(\d{5})(\d{3})/, '$1-$2');
            setEnderecoDTO({ ...enderecoDTO, cep: formattedCep });
        }

        if (newCep.length === 8) {
            try {
                const addressData = await fetchAddressByCep(newCep);
                setEnderecoDTO({ ...enderecoDTO, ...addressData });
            } catch (error) {
                console.error('Erro ao buscar endereço:', error);
            }
        }
    };

    const formatCep = (cep) => {
        return cep.replace(/(\d{5})(\d{3})/, '$1-$2');
    };

    return (
        <div className="modal-overlay">
            <div className="modal-content-editar-perfil">
                <button className="modal-close" onClick={onClose}>×</button>
                <div className="form-container-endereco">
                    <h2>Editar Endereço</h2>
                    <form onSubmit={handleSubmit} className="form-grid">
                        <div className="form-group">
                            <input
                                type="text"
                                placeholder="CEP"
                                value={enderecoDTO.cep || ''}
                                onChange={handleCepChange}
                                required
                            />
                        </div>
                        <div className="form-group">
                            <input
                                type="text"
                                placeholder="Rua"
                                value={enderecoDTO.rua}
                                onChange={(e) => setEnderecoDTO({ ...enderecoDTO, rua: e.target.value })}
                                required
                            />
                        </div>
                        <div className="form-group">
                            <input
                                type="text"
                                placeholder="Número"
                                value={enderecoDTO.numero}
                                onChange={(e) => setEnderecoDTO({ ...enderecoDTO, numero: e.target.value })}
                                required
                            />
                        </div>
                        <div className="form-group">
                            <input
                                type="text"
                                placeholder="Cidade"
                                value={enderecoDTO.cidade}
                                onChange={(e) => setEnderecoDTO({ ...enderecoDTO, cidade: e.target.value })}
                                required
                            />
                        </div>
                        <div className="form-group">
                            <input
                                type="text"
                                placeholder="Estado"
                                value={enderecoDTO.estado}
                                onChange={(e) => setEnderecoDTO({ ...enderecoDTO, estado: e.target.value })}
                                required
                            />
                        </div>
                        <div className="form-group">
                            <input
                                type="text"
                                placeholder="País"
                                value={enderecoDTO.pais}
                                onChange={(e) => setEnderecoDTO({ ...enderecoDTO, pais: e.target.value })}
                                required
                            />
                        </div>
                        <div className="form-group">
                            <input
                                type="text"
                                placeholder="Complemento"
                                value={enderecoDTO.complemento}
                                onChange={(e) => setEnderecoDTO({ ...enderecoDTO, complemento: e.target.value })}
                            />
                        </div>
                        <div className="form-group-full">
                            <input
                                type="password"
                                placeholder="Senha"
                                value={enderecoSenha}
                                onChange={(e) => setEnderecoSenha(e.target.value)}
                                required
                            />
                        </div>
                        <div className="form-group-full">
                            <Button
                                type="button-register"
                                style={{ backgroundColor: '#e65c00', color: '#fff' }}
                                onClick={handleSubmit}
                            >
                                Salvar Endereço
                            </Button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    );
};

ModalEndereco.propTypes = {
    isOpen: PropTypes.bool.isRequired,
    onClose: PropTypes.func.isRequired,
    enderecoDTO: PropTypes.object.isRequired,
    setEnderecoDTO: PropTypes.func.isRequired,
    enderecoSenha: PropTypes.string.isRequired,
    setEnderecoSenha: PropTypes.func.isRequired,
    handleSubmit: PropTypes.func.isRequired,
};

export default ModalEndereco;
