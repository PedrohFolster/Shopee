export const isValidNomeCompleto = (nome) => {
    return nome && nome.split(' ').length >= 2;
};

export const isValidEmail = (email) => {
    return email && /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
};

export const isValidDataNascimento = (dataNascimento) => {
    if (!dataNascimento) return false;
    const birthDate = new Date(dataNascimento);
    const today = new Date();
    const age = today.getFullYear() - birthDate.getFullYear();
    const monthDiff = today.getMonth() - birthDate.getMonth();
    if (monthDiff < 0 || (monthDiff === 0 && today.getDate() < birthDate.getDate())) {
        return age - 1 >= 12;
    }
    return age >= 12;
};

export const isValidTelefone = (telefone) => {
    return telefone && /^\d{11}$/.test(telefone.replace(/\D/g, ''));
}; 