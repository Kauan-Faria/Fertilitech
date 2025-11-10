import { useForm } from "react-hook-form";
import { yupResolver } from "@hookform/resolvers/yup";
import * as yup from "yup";
import styled from "styled-components";
import api from "../api/api";
import { useNavigate } from "react-router-dom";
import Button from "../components/Button";

const schema = yup.object().shape({
  fullName: yup.string().required("Nome completo é obrigatório"),
  email: yup.string().email("Formato inválido").required("E-mail é obrigatório"),
  phone: yup.string().required("Telefone é obrigatório"),
  gender: yup.string().required("Gênero é obrigatório"),
  address: yup.string().required("Endereço é obrigatório"),
  emergency: yup.string().required("Contato de emergência obrigatório"),
  healthPlan: yup.string().required("Informe se possui plano de saúde"),
  planNumber: yup.string().when("healthPlan", {
    is: (val) => val !== "Nenhum",
    then: (schema) => schema.required("Número do plano é obrigatório"),
  }),
  allergy: yup.string().required("Campo obrigatório"),
  takesMeds: yup.string().required("Campo obrigatório"),
  medications: yup.string().when("takesMeds", {
    is: "Sim",
    then: (schema) => schema.required("Informe as medicações"),
  }),
});

const Container = styled.div`
  background-color: #121212;
  min-height: 100vh;
  display: flex;
  justify-content: center;
  padding: 2rem 1rem;
`;

const Card = styled.div`
  background-color: #1e1e1e;
  padding: 3rem 2rem;
  border-radius: 16px;
  width: 100%;
  max-width: 600px;
  box-shadow: 0 0 15px rgba(0, 0, 0, 0.2);
`;

const Title = styled.h2`
  color: #e91e63;
  text-align: center;
  margin-bottom: 2rem;
`;

const Form = styled.form`
  display: flex;
  flex-direction: column;
  gap: 1.2rem;
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

const Error = styled.span`
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

const PatientSignup = () => {
  const navigate = useNavigate();
  const {
    register,
    handleSubmit,
    watch,
    formState: { errors },
  } = useForm({ resolver: yupResolver(schema) });

  const takesMeds = watch("takesMeds");

  const onSubmit = async (data) => {
    try {
      await api.post("/patients", data);
      navigate("/signup/success");
    } catch (err) {
      console.error("Erro ao cadastrar:", err);
    }
  };

  return (
    <Container>
      <Card>
        <Title>Cadastro do Paciente</Title>
        <Form onSubmit={handleSubmit(onSubmit)}>
          <FieldGroup>
            <Label>Nome completo</Label>
            <StyledInput {...register("fullName")} />
            <Error>{errors.fullName?.message}</Error>
          </FieldGroup>

          <FieldGroup>
            <Label>Email</Label>
            <StyledInput {...register("email")} />
            <Error>{errors.email?.message}</Error>
          </FieldGroup>

          <FieldGroup>
            <Label>Telefone</Label>
            <StyledInput {...register("phone")} />
            <Error>{errors.phone?.message}</Error>
          </FieldGroup>

          <FieldGroup>
            <Label>Gênero</Label>
            <StyledInput {...register("gender")} />
            <Error>{errors.gender?.message}</Error>
          </FieldGroup>

          <FieldGroup>
            <Label>Endereço</Label>
            <StyledInput {...register("address")} />
            <Error>{errors.address?.message}</Error>
          </FieldGroup>

          <FieldGroup>
            <Label>Contato de emergência</Label>
            <StyledInput {...register("emergency")} />
            <Error>{errors.emergency?.message}</Error>
          </FieldGroup>

          <FieldGroup>
            <Label>Plano de saúde</Label>
            <StyledInput {...register("healthPlan")} />
            <Error>{errors.healthPlan?.message}</Error>
          </FieldGroup>

          <FieldGroup>
            <Label>Número do plano</Label>
            <StyledInput {...register("planNumber")} />
            <Error>{errors.planNumber?.message}</Error>
          </FieldGroup>

          <FieldGroup>
            <Label>Tem alergia a medicamentos?</Label>
            <StyledInput {...register("allergy")} />
            <Error>{errors.allergy?.message}</Error>
          </FieldGroup>

          <FieldGroup>
            <Label>Faz uso de medicação diária? (Sim ou Não)</Label>
            <StyledInput {...register("takesMeds")} />
            <Error>{errors.takesMeds?.message}</Error>
          </FieldGroup>

          {takesMeds === "Sim" && (
            <FieldGroup>
              <Label>Quais medicações?</Label>
              <StyledInput {...register("medications")} />
              <Error>{errors.medications?.message}</Error>
            </FieldGroup>
          )}

          <StyledButton type="submit">Cadastrar</StyledButton>
        </Form>
      </Card>
    </Container>
  );
};

export default PatientSignup;
