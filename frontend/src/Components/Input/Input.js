import React from 'react';
import './Input.css';

const Input = ({ type = 'text', id, name, value, onChange, placeholder, required = false, readOnly = false, options = [] }) => {
  if (type === 'select') {
    return (
      <div className="input-group">
        <select
          id={id}
          name={name}
          value={value}
          onChange={onChange}
          required={required}
          className="custom-input"
        >
          <option value="">{placeholder}</option>
          {options.map((option, index) => (
            <option key={index} value={option}>{option}</option>
          ))}
        </select>
      </div>
    );
  }

  return (
    <div className="input-group">
      <input
        type={type}
        id={id}
        name={name}
        value={value}
        onChange={readOnly ? undefined : onChange} 
        placeholder={placeholder}
        required={required}
        readOnly={readOnly} 
        className="custom-input"
      />
    </div>
  );
};

export default Input;
