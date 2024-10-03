import React from 'react';
import Header from '../../Components/Menu/Items/Header/Header';
import '../CSS/Home.css';


const Home = () => {
  return (
    <div className='home'>
      <Header />
      <main className='home-content'>
        <h1>Bem-vindo à Página Inicial</h1>
        <p>Este é o conteúdo da página inicial.</p>
      </main>
    </div>
  );
};

export default Home;
