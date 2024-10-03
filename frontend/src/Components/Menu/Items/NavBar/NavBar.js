import React from 'react';
import { Link } from 'react-router-dom';
import './NavBar.css';

const categories = [
  "DEPARTAMENTOS",
  "CUPONS",
  "MONTE SEU PC",
  "OFERTAS DO DIA",
  "KBM! GAMING",
  "LOJAS OFICIAIS",
  "HARDWARE",
  "COMPUTADORES",
  "PERIFÉRICOS",
  "QUEIMA TOTAL"
];

const NavBar = () => {
  return (
    <nav className="navbar">
      <div className="navbar-content">
        <ul className="navbar-list">
          {categories.map((category, index) => (
            <React.Fragment key={index}>
              <li className="navbar-item">
                <Link to={`/categoria/${category.toLowerCase().replace(/\s+/g, '-')}`}>
                  {category}
                </Link>
              </li>
              {index < categories.length - 1 && <li className="navbar-separator">|</li>}
            </React.Fragment>
          ))}
        </ul>
      </div>
    </nav>
  );
};

export default NavBar;
