import React from "react";
import styled from "styled-components";
import { useNavigate } from "react-router-dom";
import Button from "../components/Button";
import LogoImg from "../assets/logo.png";

const Container = styled.div`
  height: 100vh;
  background-color: #121212;
  display: flex;
  justify-content: center;
  align-items: center;
`;

const Content = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
`;

const Header = styled.div`
  display: flex;
  align-items: center;
  gap: 1rem;
  margin-bottom: 1rem;
`;

const Logo = styled.img`
  width: 48px;
  height: 48px;
`;

const Title = styled.h1`
  color: #e91e63;
  font-size: 2rem;
`;

const Subtitle = styled.p`
  color: white;
  font-size: 1rem;
  max-width: 300px;
  text-align: center;
  margin-bottom: 2rem;
`;

const StyledButton = styled(Button)`
  background-color: #b03060;
  font-weight: bold;
  width: 250px;
  font-size: 1rem;
  padding: 0.9rem;
  margin-bottom: 1rem;

  &:hover {
    background-color: #8a244c;
  }
`;

export default function LandingPage() {
  const navigate = useNavigate();

  return (
    <Container>
      <Content>
        <Header>
          <Logo src={LogoImg} alt="Logo FertiliTech" />
          <Title>FertiliTech</Title>
        </Header>

        <Subtitle>
          Seja bem-vindo à FertiliTech, sua plataforma que te ajudará a trazer uma vida a você!
        </Subtitle>

        <StyledButton onClick={() => navigate("/patient/login")}>
          Entrar como Paciente
        </StyledButton>
        <StyledButton onClick={() => navigate("/signup")}>
          Criar Cadastro
        </StyledButton>
        <StyledButton onClick={() => navigate("/admin/login")}>
          Administrador
        </StyledButton>
      </Content>
    </Container>
  );
}
