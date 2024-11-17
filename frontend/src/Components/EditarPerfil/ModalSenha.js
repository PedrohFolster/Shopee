import React from 'react';
import PropTypes from 'prop-types';
import Button from '../../Components/Button/Button';

const ModalSenha = ({ isOpen, onClose, senhaAtual, setSenhaAtual, novaSenha, setNovaSenha, confirmarNovaSenha, setConfirmarNovaSenha, handleSubmit }) => {
    if (!isOpen) return null;

    return (
        <div className="modal-overlay">
            <div className="modal-content-editar-perfil">
                <button className="modal-close" onClick={onClose}>×</button>
                <div className="form-container-endereco">
                    <h2>Mudar Senha</h2>
                    <form onSubmit={handleSubmit} className="form-grid">
                        <div className="form-group-full">
                            <input
                                type="password"
                                placeholder="Senha Atual"
                                value={senhaAtual}
                                onChange={(e) => setSenhaAtual(e.target.value)}
                                required
                            />
                        </div>
                        <div className="form-group-full">
                            <input
                                type="password"
                                placeholder="Nova Senha"
                                value={novaSenha}
                                onChange={(e) => setNovaSenha(e.target.value)}
                                required
                            />
                        </div>
                        <div className="form-group-full">
                            <input
                                type="password"
                                placeholder="Confirmar Nova Senha"
                                value={confirmarNovaSenha}
                                onChange={(e) => setConfirmarNovaSenha(e.target.value)}
                                required
                            />
                        </div>
                        <div className="form-group-full">
                            <Button
                                type="button-register"
                                style={{ backgroundColor: '#e65c00', color: '#fff' }}
                                onClick={handleSubmit}
                            >
                                Salvar Senha
                            </Button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    );
};

ModalSenha.propTypes = {
    isOpen: PropTypes.bool.isRequired,
    onClose: PropTypes.func.isRequired,
    senhaAtual: PropTypes.string.isRequired,
    setSenhaAtual: PropTypes.func.isRequired,
    novaSenha: PropTypes.string.isRequired,
    setNovaSenha: PropTypes.func.isRequired,
    confirmarNovaSenha: PropTypes.string.isRequired,
    setConfirmarNovaSenha: PropTypes.func.isRequired,
    handleSubmit: PropTypes.func.isRequired,
};

export default ModalSenha;
