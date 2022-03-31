import { Button, Card, Col, Container, Form, Row } from "react-bootstrap";
import { useForm, useFormState } from "react-hook-form";
import { registerUser } from "./api/UserApi";
import toast from "react-hot-toast";
import { useHistory } from "react-router-dom";
import { createStatForGame } from "./api/StatisticApi";
import { signUpUser } from "./api/SecurityApi";
import "./RegisterForm.css";
import "./games/GameBtn.css";

const RegisterForm = ({ location }) => {
  const { register, control, handleSubmit, reset, getValues } = useForm();
  const { isSubmitting } = useFormState({ control });
  const history = useHistory();
  const gameId = location.state;

  const handleRegistrationSubmit = async (userInput) => {
    try {
      await registerUser(userInput);
      await signUpUser({
        username: userInput.username,
        password: userInput.password,
      });
      if (gameId) {
        await createStatForGame({
          gamerName: userInput.username,
          gameId: gameId,
        });
      }
      toast.success("Registration success! Now you are logged in!");
      history.push("/");
    } catch (error) {
      toast.error(error.message);
      reset({ ...getValues(), username: "", password: "" });
    }
  };
  return (
    <div className="reg-form-area">
      <Container fluid>
        <Row className="vh-100 justify-content-center align-items-center">
          <Col md="5">
            <Card>
              <Card.Header>
                <strong>Please, fill out the registration form</strong>
              </Card.Header>
              <Card.Body>
                <Form onSubmit={handleSubmit(handleRegistrationSubmit)}>
                  <Form.Group
                    controlId="formBasicFName"
                    className="reg-form-input"
                  >
                    <Form.Label>First Name </Form.Label>
                    <Form.Control
                      {...register("firstName")}
                      type="text"
                      required
                      placeholder="..."
                    />
                  </Form.Group>
                  <Form.Group
                    controlId="formBasicLName"
                    className="reg-form-input"
                  >
                    <Form.Label>Last Name </Form.Label>
                    <Form.Control
                      {...register("lastName")}
                      type="text"
                      required
                      placeholder="..."
                    />
                  </Form.Group>
                  <Form.Group
                    controlId="formBasicUsername"
                    className="reg-form-input"
                  >
                    <Form.Label>Username </Form.Label>
                    <Form.Control
                      {...register("username")}
                      type="text"
                      required
                      placeholder="..."
                    />
                  </Form.Group>
                  <Form.Group
                    controlId="formBasicPassword"
                    className="reg-form-input"
                  >
                    <Form.Label>Password </Form.Label>
                    <Form.Control
                      {...register("password")}
                      type="password"
                      required
                      placeholder="..."
                    />
                  </Form.Group>
                  <br />
                  <Button
                    className="game-btn  game-btn--small"
                    disabled={isSubmitting}
                    variant="primary"
                    type="submit"
                  >
                    Register
                  </Button>
                </Form>
              </Card.Body>
            </Card>
          </Col>
        </Row>
      </Container>
    </div>
  );
};

export default RegisterForm;
