import styled from "styled-components";
import PropTypes from "prop-types";

const InputWrapper = styled.div`
  display:flex;flex-direction:column;gap:0.25rem;
`;
const StyledLabel = styled.label`
  font-weight:500;
`;
const StyledInput = styled.input`
  padding:0.75rem 1rem;border:1px solid #D1D5DB;border-radius:0.5rem;
  &:focus{outline:none;border-color:${({theme})=>theme.colors.primary};box-shadow:0 0 0 2px rgba(99,102,241,0.3);}  
`;
const ErrorMsg = styled.span`
  color:#DC2626;font-size:0.875rem;
`;

const Input = ({ label, register, name, error, ...rest }) => (
  <InputWrapper>
    {label && <StyledLabel htmlFor={name}>{label}</StyledLabel>}
    <StyledInput id={name} {...register(name)} {...rest} />
    {error && <ErrorMsg>{error.message}</ErrorMsg>}
  </InputWrapper>
);

Input.propTypes = {
  label: PropTypes.string,
  register: PropTypes.func.isRequired,
  name: PropTypes.string.isRequired,
  error: PropTypes.object,
};
export default Input;