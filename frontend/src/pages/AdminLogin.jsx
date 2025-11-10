import { useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import { useState } from "react";
import axios from "axios";
import Button from "../components/Button";

const Container = styled.div`
  height: 100vh;
  background-color: #121212;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 1rem;
`;

const Card = styled.div`
  background-color: #1e1e1e;
  padding: 3rem 2rem;
  border-radius: 16px;
  width: 100%;
  max-width: 400px;
  box-shadow: 0 0 20px rgba(0, 0, 0, 0.2);
  text-align: center;
`;

const Title = styled.h2`
  color: #e91e63;
  margin-bottom: 2rem;
`;

const Form = styled.form`
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
  text-align: left;
`;

const FieldGroup = styled.div`
  display: flex;
  flex-direction: column;
`;

const Label = styled.label`
  color: white;
  font-size: 0.9rem;
  margin-bottom: 0.4rem;
`;

const StyledInput = styled.input`
  padding: 0.75rem;
  border-radius: 8px;
  border: 1px solid #ccc;
  background-color: #fff;
  font-size: 1rem;
`;

const ErrorMessage = styled.p`
  color: #ff5c5c;
  font-size: 0.8rem;
  margin: -0.5rem 0 0 0;
`;

const StyledButton = styled(Button)`
  background-color: #b03060;
  font-weight: bold;

  &:hover {
    background-color: #8a244c;
  }
`;

const AdminLogin = () => {
  const { register, handleSubmit } = useForm();
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  const onSubmit = async (data) => {
    try {
      const response = await axios.post("http://localhost:4000/admin/login", data);
      if (response.data.success) {
        navigate("/admin/dashboard");
      } else {
        setError("Credenciais inválidas");
      }
    } catch (err) {
      console.error(err);
      setError("Erro no servidor. Tente novamente.");
    }
  };

  return (
    <Container>
      <Card>
        <Title>Login do Administrador</Title>
        <Form onSubmit={handleSubmit(onSubmit)}>
          <FieldGroup>
            <Label>Email</Label>
            <StyledInput
              type="email"
              placeholder="admin@fertilitech.com"
              {...register("email", { required: true })}
            />
          </FieldGroup>

          <FieldGroup>
            <Label>Senha</Label>
            <StyledInput
              type="password"
              placeholder="Senha"
              {...register("password", { required: true })}
            />
          </FieldGroup>

          {error && <ErrorMessage>{error}</ErrorMessage>}

          <StyledButton type="submit">Entrar</StyledButton>
        </Form>
      </Card>
    </Container>
  );
};

export default AdminLogin;
