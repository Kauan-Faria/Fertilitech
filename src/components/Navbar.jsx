import { Link } from "react-router-dom";
import styled from "styled-components";

const Nav = styled.nav`
  background-color: #1e40af;
  padding: 1rem;
  display: flex;
  justify-content: center;
  gap: 2rem;
`;

const StyledLink = styled(Link)`
  color: white;
  font-weight: bold;
  text-decoration: none;

  &:hover {
    text-decoration: underline;
  }
`;

const Navbar = () => {
  return (
    <Nav>
      <StyledLink to="/signup">Cadastro</StyledLink>
      <StyledLink to="/appointment">Agendamento</StyledLink>
      <StyledLink to="/dashboard">Dashboard</StyledLink>
      <StyledLink to="/admin/login">Área Admin</StyledLink>
    </Nav>
  );
};

export default Navbar;
