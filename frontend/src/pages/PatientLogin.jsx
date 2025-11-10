import { useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import Button from "../components/Button";
import PatientDashboard from "./PatientDashboard";

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
  box-shadow: 0 0 20px rgba(0, 0, 0, 0.2);
  width: 100%;
  max-width: 400px;
  text-align: center;
`;

const Title = styled.h2`
  color: white;
  margin-bottom: 1.5rem;
`;

const StyledForm = styled.form`
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

const ErrorMessage = styled.span`
  color: #ff5c5c;
  font-size: 0.75rem;
  margin-top: 0.3rem;
`;

const StyledButton = styled(Button)`
  background-color: #b03060;
  font-weight: bold;

  &:hover {
    background-color: #8a244c;
  }
`;

export default function PatientLogin() {
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm();

  const navigate = useNavigate();

  const onSubmit = (data) => {
    if (data.email === "teste@fertilitech.com" && data.password === "123456") {
      navigate("/dashboard");
    } else {
      alert("Email ou senha incorretos.");
    }
  };

  return (
    <Container>
      <Card>
        <Title>Login do Paciente</Title>

        <StyledForm onSubmit={handleSubmit(onSubmit)}>
          <FieldGroup>
            <Label>Email</Label>
            <StyledInput
              type="email"
              placeholder="email@exemplo.com"
              {...register("email", {
                required: "O email é obrigatório",
                pattern: {
                  value: /^[^\s@]+@[^\s@]+\.[^\s@]+$/,
                  message: "Email inválido",
                },
              })}
            />
            {errors.email && <ErrorMessage>{errors.email.message}</ErrorMessage>}
          </FieldGroup>

          <FieldGroup>
            <Label>Senha</Label>
            <StyledInput
              type="password"
              placeholder="Digite sua senha"
              {...register("password", {
                required: "A senha é obrigatória",
                minLength: {
                  value: 6,
                  message: "A senha deve ter no mínimo 6 caracteres",
                },
              })}
            />
            {errors.password && <ErrorMessage>{errors.password.message}</ErrorMessage>}
          </FieldGroup>

          <StyledButton type="submit">Entrar</StyledButton>
        </StyledForm>
      </Card>
    </Container>
  );
}
