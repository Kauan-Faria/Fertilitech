import { useForm } from "react-hook-form";
import styled from "styled-components";
import api from "../api/api";
import { useNavigate } from "react-router-dom";
import Button from "../components/Button";

const Page = styled.div`
  background-color: #121212;
  min-height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 2rem;
`;

const Card = styled.div`
  background-color: #1e1e1e;
  padding: 3rem;
  border-radius: 16px;
  width: 100%;
  max-width: 600px;
  box-shadow: 0 0 30px rgba(0, 0, 0, 0.3);
  color: white;
`;

const Title = styled.h2`
  text-align: center;
  color: #e91e63;
  margin-bottom: 2.5rem;
`;

const Form = styled.form`
  display: flex;
  flex-direction: column;
  gap: 1.8rem;
`;

const Field = styled.div`
  display: flex;
  flex-direction: column;
`;

const Label = styled.label`
  font-weight: bold;
  margin-bottom: 0.5rem;
  color: #e0e0e0;
`;

const Input = styled.input`
  padding: 0.75rem;
  border-radius: 8px;
  border: none;
  background-color: #2a2a2a;
  color: white;

  &:focus {
    outline: 2px solid #e91e63;
  }
`;

const Select = styled.select`
  padding: 0.75rem;
  border-radius: 8px;
  border: none;
  background-color: #2a2a2a;
  color: white;

  &:focus {
    outline: 2px solid #e91e63;
  }
`;

const Error = styled.span`
  color: #ff4d4d;
  font-size: 0.9rem;
`;

const StyledButton = styled(Button)`
  margin-top: 1rem;
  font-size: 1rem;
  background-color: #b03060;

  &:hover {
    background-color: #8a244c;
  }
`;

export default function AppointmentBooking() {
  const {
    register,
    handleSubmit,
    formState: { errors }
  } = useForm();
  const navigate = useNavigate();

  const onSubmit = async (data) => {
    try {
      await api.post("/appointments", data);
      navigate("/dashboard");
    } catch (err) {
      console.error("Erro ao agendar consulta:", err);
    }
  };

  return (
    <Page>
      <Card>
        <Title>Agendar Consulta</Title>
        <Form onSubmit={handleSubmit(onSubmit)}>
          <Field>
            <Label>ID do Paciente</Label>
            <Input {...register("patientId", { required: true })} />
            {errors.patientId && <Error>Campo obrigatório</Error>}
          </Field>

          <Field>
            <Label>Especialidade / Médico</Label>
            <Select {...register("doctor", { required: true })}>
              <option value="">Selecione uma opção</option>
              <option value="Reprodução Humana">Reprodução Humana</option>
              <option value="Ginecologista">Ginecologista</option>
              <option value="Cirurgião Ginecológico">Cirurgião Ginecológico</option>
            </Select>
            {errors.doctor && <Error>Campo obrigatório</Error>}
          </Field>

          <Field>
            <Label>Motivo da Consulta</Label>
            <Input {...register("reason", { required: true })} />
            {errors.reason && <Error>Campo obrigatório</Error>}
          </Field>

          <Field>
            <Label>Data e Hora</Label>
            <Input type="datetime-local" {...register("date", { required: true })} />
            {errors.date && <Error>Campo obrigatório</Error>}
          </Field>

          <StyledButton type="submit">Agendar Consulta</StyledButton>
        </Form>
      </Card>
    </Page>
  );
}
