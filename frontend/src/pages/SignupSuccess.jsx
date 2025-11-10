import Container from "../components/Layout";
import { Link } from "react-router-dom";
import Button from "../components/Button";

const SignupSuccess = () => (
  <Container style={{textAlign:"center",marginTop:"4rem"}}>
    <h2>Cadastro enviado com sucesso!</h2>
    <p>Agora você pode marcar sua consulta.</p>
    <Link to="/appointment"><Button>Marcar Consulta</Button></Link>
  </Container>
);
export default SignupSuccess;
