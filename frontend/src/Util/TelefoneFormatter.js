export const formatarTelefone = (telefone) => {
    const telefoneNumeros = telefone.replace(/\D/g, '');
    const tamanho = telefoneNumeros.length;

    if (tamanho > 13) return telefone.slice(0, 12);
    if (tamanho === 0) return '';

    let formattedNumber = '';

    if (tamanho <= 2) {
        formattedNumber = `(${telefoneNumeros}`;
    } else if (tamanho <= 4) {
        formattedNumber = `(${telefoneNumeros.slice(0, 2)}) ${telefoneNumeros.slice(2)}`;
    } else if (tamanho <= 8) {
        formattedNumber = `(${telefoneNumeros.slice(0, 2)}) ${telefoneNumeros.slice(2, 4)}${telefoneNumeros.slice(4)}`;
    } else {
        formattedNumber = `(${telefoneNumeros.slice(0, 2)}) ${telefoneNumeros.slice(2, 4)}${telefoneNumeros.slice(4, 9)}${telefoneNumeros.slice(9, 13)}`;
    }

    return formattedNumber;
};