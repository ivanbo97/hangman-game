import { Button, Card, Col, Container, Form, Row } from "react-bootstrap";
import { Link, useHistory } from "react-router-dom";
import { useForm, useFormState } from "react-hook-form";
import { signUpUser } from "./api/SecurityApi";
import toast from "react-hot-toast";
import { createStatForGame } from "./api/StatisticApi";
import "./SignUpForm.css";
import "./games/GameBtn.css";

const SignUpForm = ({ location }) => {
  const gameData = location.state;
  const { register, control, handleSubmit } = useForm();
  const { isSubmitting } = useFormState({ control });
  const history = useHistory();

  const handleSignIn = async (userData) => {
    try {
      await signUpUser(userData);
      if (gameData) {
        await createStatForGame({
          gamerName: userData.username,
          gameId: gameData.gameId,
        });
      }
      toast.success("You have logged in!!!");
      history.push("/");
    } catch (error) {
      toast.error(error.message);
    }
  };
  return (
    <div className="signup-form-area">
      <Container fluid>
        <Row className="vh-100 justify-content-center align-items-center">
          <Col md="5">
            <Card>
              <Card.Header>
                <strong>Log into your account</strong>
              </Card.Header>
              <Card.Body>
                <Form onSubmit={handleSubmit(handleSignIn)}>
                  <Form.Group
                    controlId="formBasicUsername"
                    className="signup-form-input"
                  >
                    <Form.Label>Username </Form.Label>
                    <Form.Control
                      {...register("username")}
                      type="text"
                      placeholder="..."
                      name="username"
                    />
                  </Form.Group>
                  <Form.Group
                    controlId="formBasicPassword"
                    className="signup-form-input"
                  >
                    <Form.Label>Password </Form.Label>
                    <Form.Control
                      {...register("password")}
                      type="password"
                      placeholder="..."
                      name="password"
                    />
                  </Form.Group>
                  <br />
                  <Button
                    className="game-btn game-btn--small"
                    disabled={isSubmitting}
                    variant="primary"
                    type="submit"
                  >
                    Sign in
                  </Button>
                  <br />
                  <br />
                  <Link to={{ pathname: "register", state: gameData?.gameId }}>
                    Not having an account ? Register here...
                  </Link>
                </Form>
              </Card.Body>
            </Card>
          </Col>
        </Row>
      </Container>
    </div>
  );
};

export default SignUpForm;
