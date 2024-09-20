import styled from 'styled-components';
import { Link } from 'react-router-dom';

export const Header = styled.header`
  background-color: rgb(0, 96, 177);
  color: #fff;
  padding: 2.3rem 2rem;
  display: flex;
  align-items: center;
  justify-content: space-between;
`;

export const HeaderContent = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 100%;
`;

export const HeaderLeft = styled.div`
  display: flex;
  align-items: center;
`;

export const HeaderRight = styled.div`
  display: flex;
  align-items: center;
  gap: 15px; /* Espaço entre os botões */
`;

export const MenuButton = styled.button`
  position: relative;
  width: 30px;
  height: 20px;
  background: none;
  border: none;
  cursor: pointer;
  transition: all 0.3s ease;

  .line {
    position: absolute;
    width: 100%;
    height: 3px;
    background: white;
    transition: all 0.3s ease;

    &:nth-child(1) {
      top: 0;
    }

    &:nth-child(2) {
      top: 50%;
      transform: translateY(-50%);
    }

    &:nth-child(3) {
      bottom: 0;
    }
  }

  &.open .line:nth-child(1) {
    transform: rotate(45deg);
    top: 50%;
    transform: translateY(-50%) rotate(45deg);
  }

  &.open .line:nth-child(2) {
    opacity: 0;
  }

  &.open .line:nth-child(3) {
    transform: rotate(-45deg);
    bottom: 50%;
    transform: translateY(50%) rotate(-45deg);
  }
`;

export const LogoLink = styled.a`
  display: block;
  padding-left: 3rem;

  img {
    display: block;
    padding: 0rem;
  }
`;


export const AuthLink = styled(Link)`
  display: flex;
  align-items: center;
  text-decoration: none;
  color: inherit;
  font-size: 16px;
  padding: 10px;

  .icon {
    margin-right: 12px; /* Ajuste o espaço conforme necessário */
  }

  &:hover {
    color: #000;
  }

  &.active {
    font-weight: bold;
  }
`;

export const AuthButton = styled.button`
  background-color: rgb(0, 96, 177);
  color: #fff;
  border: none;
  padding: 10px 20px;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;
  transition: background-color 0.3s ease;

  &:hover {
    background-color: rgb(0, 76, 144);
  }
`;

export const Icon = styled.i`
  font-size: 20px; /* Ajuste o tamanho conforme necessário */
  color: #fff;
  margin-right: 16px; /* Aumente o espaço entre o ícone e o texto */
`;

export const SearchContainer = styled.div`
  flex-grow: 1;
  display: flex;
  justify-content: center;
  margin: 0 2rem;
`;

export const SearchInput = styled.input`
  width: 100%;
  max-width: 500px;
  padding: 10px;
  border: 1px solid #fff;
  border-radius: 5px;
  background-color: #fff;
  color: #000;
  font-size: 16px;
  outline: none;
  margin: 0px 70px;

  @media (max-width: 768px) {
    max-width: 300px;
  }
`;
