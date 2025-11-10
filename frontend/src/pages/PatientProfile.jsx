import Container from "../components/Layout";
import Input from "../components/Input";
import Button from "../components/Button";
import { useForm } from "react-hook-form";
import styled from "styled-components";

const Form = styled.form`
  display: flex; flex-direction: column; gap: 1rem; max-width: 600px; margin: 2rem auto;
`;

const PatientProfile = () => {
  const { register, handleSubmit } = useForm();

  const onSubmit = (data) => {
    alert("Perfil atualizado com sucesso!");
    console.log(data);
  };

  return (
    <Container>
      <h2>Informações do Perfil</h2>
      <Form onSubmit={handleSubmit(onSubmit)}>
        <Input label="Gênero" name="gender" register={register} placeholder="Ex: Feminino" />
        <Input label="Endereço" name="address" register={register} placeholder="Rua, número, bairro" />
        <Input label="Contato de Emergência" name="emergency" register={register} placeholder="(00) 00000-0000" />
        <Input label="Plano de Saúde" name="healthPlan" register={register} placeholder="Nome do plano" />
        <Input label="Número do Plano" name="planNumber" register={register} placeholder="123456789" />
        <Input label="Alergia a Medicamentos" name="allergy" register={register} placeholder="Informe caso tenha" />
        <Input label="Faz uso de medicações diárias?" name="takesMeds" register={register} placeholder="Sim ou Não" />
        <Input label="Quais medicações?" name="medications" register={register} placeholder="Se sim, liste aqui" />
        <Button type="submit">Salvar</Button>
      </Form>
    </Container>
  );
};
export default PatientProfile;