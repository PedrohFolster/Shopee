import React from 'react';
import Header from '../Components/Menu/Items/Header/Header';

const Home = () => {
  return (
    <div>
      <Header />
      {/* Adicione o conteúdo da página Home aqui */}
      <main>
        <h1>Bem-vindo à Página Inicial</h1>
        <p>Este é o conteúdo da página inicial.</p>
      </main>
    </div>
  );
};

export default Home;
