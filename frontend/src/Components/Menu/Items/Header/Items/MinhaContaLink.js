import React, { useState, useContext } from 'react';
import { Link, useNavigate } from 'react-router-dom'; 
import '../Header.css';
import { faUser, faClipboardList, faSignOutAlt, faCaretDown, faCaretUp, faStore } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { AuthContext } from '../../../../../Util/Authentication';
import axios from 'axios';


axios.defaults.withCredentials = true;

const MinhaContaLink = ({ activeLink, handleSetActive, usuario }) => {
    const [isDropdownVisible, setDropdownVisible] = useState(false);
    const { isAuthenticated, logout } = useContext(AuthContext);
    const navigate = useNavigate(); 
    let timer;

    const handleMouseEnter = () => {
        clearTimeout(timer);
        setDropdownVisible(true);
    };

    const handleMouseLeave = () => {
        timer = setTimeout(() => {
            setDropdownVisible(false);
        }, 100); 
    };

    const handleLogout = async () => {
        try {
            await logout(); 
            handleSetActive("");
            navigate('/login'); 
        } catch (error) {
            console.error("Erro ao fazer logout:", error);
        }
    };

    const handleLojaClick = async () => {
        if (!isAuthenticated) {
            navigate('/login');
            return;
        }

        try {
            const response = await axios.get(`${process.env.REACT_APP_API_URL}/lojas/verificar-loja`, { withCredentials: true });
            if (response.data === "Redirecionar para /minha-loja") {
                navigate("/MinhaLoja");
            } else {
                navigate("/CreateLoja");
            }
        } catch (error) {
            console.error("Erro ao verificar loja:", error);
        }
    };

    return (
        <div className="dropdown" onMouseEnter={handleMouseEnter} onMouseLeave={handleMouseLeave}>
            <button className="auth-link">
                <FontAwesomeIcon icon={faUser} className="icon" />
                Minha Conta
                <FontAwesomeIcon icon={isDropdownVisible ? faCaretUp : faCaretDown} className="caret-icon" />
            </button>
            {isDropdownVisible && (
                <div className="dropdown-menu">
                    <Link to="/editar-perfil" onClick={() => handleSetActive("perfil")}>
                        <FontAwesomeIcon icon={faUser} className="menu-icon" />
                        Perfil
                    </Link>
                    <Link to="/pedidos" onClick={() => handleSetActive("pedidos")}>
                        <FontAwesomeIcon icon={faClipboardList} className="menu-icon" />
                        Pedidos
                    </Link>
                    <Link as="button" onClick={handleLojaClick}>
                        <FontAwesomeIcon icon={faStore} className="menu-icon" />
                        Minha Loja
                    </Link>
                    <Link as="button" onClick={handleLogout}>
                        <FontAwesomeIcon icon={faSignOutAlt} className="menu-icon" />
                        Logout
                    </Link>
                </div>
            )}
        </div>
    );
};

export default MinhaContaLink;
