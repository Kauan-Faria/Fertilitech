import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import Button from "../components/Button";
import Logo from "../assets/logo.png";
import Chatbot from "../components/Chatbot";

const Layout = styled.div`
  display: flex;
  height: 100vh;
  background-color: #1e1e1e;
  color: white;
`;

const Sidebar = styled.div`
  width: 250px;
  background-color: #121212;
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 2rem 1rem;
  gap: 2rem;
`;

const LogoImg = styled.img`
  width: 100px;
  margin-bottom: 1rem;
`;

const NavButton = styled(Button)`
  width: 100%;
  font-size: 0.95rem;
  padding: 0.8rem;
  background-color: #b03060;

  &:hover {
    background-color: #8a244c;
  }
`;

const Content = styled.div`
  flex: 1;
  padding: 3rem;
  overflow-y: auto;
  display: flex;
  flex-direction: column;
`;

const Card = styled.div`
  background: #2a2a2a;
  padding: 2rem;
  border-radius: 16px;
  margin-bottom: 2rem;
  box-shadow: 0 0 12px rgba(0, 0, 0, 0.3);
`;

const ChatbotCard = styled(Card)`
  display: flex;
  flex-direction: column;
  height: 100%;
  padding: 1rem 1.5rem;
  margin-bottom: 0;
`;

const ChatbotWrapper = styled.div`
  flex: 1;
  min-height: 0; /* importante para o scroll funcionar dentro do flex container */
  display: flex;
  flex-direction: column;
`;

const Label = styled.label`
  display: block;
  margin-bottom: 0.5rem;
  color: white;
`;

const Input = styled.input`
  padding: 0.75rem;
  margin-bottom: 1.2rem;
  width: 100%;
  border-radius: 8px;
  border: 1px solid #ccc;
`;

export default function PatientDashboard() {
  const navigate = useNavigate();
  const [activeSection, setActiveSection] = useState("home");
  const [formData, setFormData] = useState({
    fullName: "Maria Oliveira",
    email: "maria@example.com",
    phone: "(11) 91234-5678",
    address: "Rua das Flores, 123",
  });

  const handleLogout = () => {
    navigate("/");
  };

  const handleChange = (e) => {
    setFormData((prev) => ({ ...prev, [e.target.name]: e.target.value }));
  };

  const handleSave = () => {
    alert("Dados atualizados com sucesso!");
    // Aqui você pode fazer um PUT/POST para API
  };

  const renderContent = () => {
    switch (activeSection) {
      case "home":
        return (
          <Card>
            <h2>Bem-vindo à sua área do paciente!</h2>
            <p>
              Aqui você pode agendar consultas, atualizar suas informações pessoais e tirar dúvidas com nosso assistente.
            </p>
          </Card>
        );

      case "Chatbot":
        return (
          <ChatbotCard>
            <h2>Assistente Virtual</h2>
            <p>Converse com nosso chatbot abaixo:</p>
            <ChatbotWrapper>
              <Chatbot />
            </ChatbotWrapper>
          </ChatbotCard>
        );

      case "profile":
        return (
          <Card>
            <h2>Seus Dados</h2>
            <form>
              <Label>Nome completo</Label>
              <Input name="fullName" value={formData.fullName} onChange={handleChange} />

              <Label>Email</Label>
              <Input name="email" type="email" value={formData.email} onChange={handleChange} />

              <Label>Telefone</Label>
              <Input name="phone" value={formData.phone} onChange={handleChange} />

              <Label>Endereço</Label>
              <Input name="address" value={formData.address} onChange={handleChange} />

              <NavButton type="button" onClick={handleSave}>
                Salvar Alterações
              </NavButton>
            </form>
          </Card>
        );

      default:
        return null;
    }
  };

  return (
    <Layout>
      <Sidebar>
        <LogoImg src={Logo} alt="FertiliTech Logo" />
        <h3 style={{ color: "#e91e63" }}>FertiliTech</h3>

        <NavButton onClick={() => navigate("/appointment")}>Marcar Consulta</NavButton>
        <NavButton onClick={() => setActiveSection("profile")}>Perfil</NavButton>
        <NavButton onClick={() => setActiveSection("Chatbot")}>Chatbot</NavButton>
        <NavButton onClick={handleLogout}>Sair</NavButton>
      </Sidebar>

      <Content>{renderContent()}</Content>
    </Layout>
  );
}