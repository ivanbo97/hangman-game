import { Button, Card, Col, Container, Form, Row } from "react-bootstrap";
import { useForm, useFormState } from "react-hook-form";
import { registerUser } from "./api/UserApi";
import "bootstrap/dist/css/bootstrap.min.css";
import toast from "react-hot-toast";
import { useHistory } from "react-router-dom";
import { createStatForGame } from "./api/StatisticApi";

const RegisterForm = ({ location }) => {
  const { register, control, handleSubmit, reset, getValues } = useForm();
  const { isSubmitting } = useFormState({ control });
  const history = useHistory();
  const gameId = location.state;

  const handleRegistrationSubmit = async (userInput) => {
    registerUser(userInput)
      .then(async () => {
        console.log("GamerName " + userInput.username);
        console.log("GameId " + gameId);
        await createStatForGame({
          gamerName: userInput.username,
          gameId: gameId,
        });
        toast.success(
          "You have successfully registered. Now you are logged in!!!"
        );
        history.push("/");
      })
      .catch((error) => {
        toast.error(error.message);
        reset({ ...getValues(), username: "", password: "" });
      });
  };
  return (
    <>
      <Container fluid>
        <Row className="vh-100 justify-content-center align-items-center">
          <Col md="5">
            <Card>
              <Card.Header>
                <strong>Plase, fill out the fields bellow</strong>
              </Card.Header>
              <Card.Body>
                <Form onSubmit={handleSubmit(handleRegistrationSubmit)}>
                  <Form.Group controlId="formBasicFName">
                    <Form.Label>First Name</Form.Label>
                    <Form.Control
                      {...register("firstName")}
                      type="text"
                      required
                      placeholder="..."
                    />
                  </Form.Group>
                  <Form.Group controlId="formBasicLName">
                    <Form.Label>Last Name</Form.Label>
                    <Form.Control
                      {...register("lastName")}
                      type="text"
                      required
                      placeholder="..."
                    />
                  </Form.Group>
                  <Form.Group controlId="formBasicEmail">
                    <Form.Label>Username</Form.Label>
                    <Form.Control
                      {...register("username")}
                      type="text"
                      required
                      placeholder="..."
                    />
                  </Form.Group>
                  <Form.Group controlId="formBasicPassword">
                    <Form.Label>Password</Form.Label>
                    <Form.Control
                      {...register("password")}
                      type="password"
                      required
                      placeholder="..."
                    />
                  </Form.Group>
                  <br />
                  <Button
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
    </>
  );
};

export default RegisterForm;
