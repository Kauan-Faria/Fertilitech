import styled from "styled-components";
import PropTypes from "prop-types";

const StyledButton = styled.button`
  padding:0.75rem 1.5rem;border:none;border-radius:0.5rem;cursor:pointer;
  background:${({variant,theme})=>variant==='secondary'?theme.colors.secondary:theme.colors.primary};
  color:#fff;font-weight:600;transition:filter 0.2s;
  &:disabled{opacity:0.5;cursor:not-allowed;}
  &:hover:not(:disabled){filter:brightness(1.05);}  
`;

const Button = ({ children, variant="primary", ...rest }) => (
  <StyledButton variant={variant} {...rest}>{children}</StyledButton>
);
Button.propTypes={children:PropTypes.node,variant:PropTypes.oneOf(["primary","secondary"])};
export default Button;