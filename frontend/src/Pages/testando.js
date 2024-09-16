import React, { useState } from "react";
import axios from "axios";

const CriarUsuario = () => {
  const [usuario, setUsuario] = useState({});
  const [mensagem, setMensagem] = useState("");

  const handleChange = (e) => {
    setUsuario({ ...usuario, [e.target.name]: e.target.value });
  };

  const formatarData = (data) => {
    const dataObj = new Date(data);
    const ano = dataObj.getFullYear();
    const mes = String(dataObj.getMonth() + 1).padStart(2, '0');
    const dia = String(dataObj.getDate()).padStart(2, '0');
    return `${ano}-${mes}-${dia}`;
  };
  

  const handleSubmit = (e) => {
    e.preventDefault();
    
    const dataFormatada = formatarData(usuario.dataNascimento);

    axios.post("http://localhost:8080/usuarios", {
      ...usuario,
      dataNascimento: dataFormatada,
      enderecoDTO: {
        cep: usuario.cep,
        rua: usuario.rua,
        numero: usuario.numero,
        cidade: usuario.cidade,
        estado: usuario.estado,
        pais: usuario.pais,
        complemento: usuario.complemento,
      },
      usuarioAutenticarDTO: {
        username: usuario.username,
        passwordHash: usuario.passwordHash,
      },
    })
    .then(() => setMensagem("Usuário criado com sucesso!"))
    .catch(() => setMensagem("Erro ao criar usuário!"));
  };

  return (
    <form onSubmit={handleSubmit}>
      {["nome", "email", "telefone", "dataNascimento", "cep", "rua", "numero", "cidade", "estado", "pais", "complemento", "username", "passwordHash"].map((field) => (
        <input key={field} type={field === "passwordHash" ? "password" : field === "dataNascimento" ? "date" : "text"} name={field} placeholder={field} onChange={handleChange} required />
      ))}
      <button type="submit">Criar Usuário</button>
      {mensagem && <p>{mensagem}</p>}
    </form>
  );
};

export default CriarUsuario;
