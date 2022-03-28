import { Button, Card, Col, Container, Form, Row } from "react-bootstrap";
import { Link } from "react-router-dom";
import { useForm, useFormState } from "react-hook-form";
import { useHistory } from "react-router-dom";
import { singUpUser } from "./api/SecurityApi";
import toast from "react-hot-toast";
import { createStatForGame } from "./api/StatisticApi";

const SignUpForm = ({ location }) => {
  const gameData = location.state;
  const { register, control, handleSubmit } = useForm();
  const { isSubmitting } = useFormState({ control });
  const history = useHistory();

  const handleSignIn = (userData) => {
    singUpUser(userData)
      .then(async () => {
        if(gameData){
        await createStatForGame({
          gamerName: userData.username,
          gameId: gameData.gameId,
        });
        }
        toast.success("You have logged in!!!");
        history.push("/");
      })
      .catch((error) => toast.error(error.message));
  };
  return (
    <>
      <Container fluid>
        <Row className="vh-100 justify-content-center align-items-center">
          <Col md="5">
            <Card>
              <Card.Header>
                <strong>Log into your accont</strong>
              </Card.Header>
              <Card.Body>
                <Form onSubmit={handleSubmit(handleSignIn)}>
                  <Form.Group controlId="formBasicEmail">
                    <Form.Label>Username</Form.Label>
                    <Form.Control
                      {...register("username")}
                      type="text"
                      placeholder="..."
                      name="username"
                    />
                  </Form.Group>
                  <Form.Group controlId="formBasicPassword">
                    <Form.Label>Password</Form.Label>
                    <Form.Control
                      {...register("password")}
                      type="password"
                      placeholder="Password"
                      name="password"
                    />
                  </Form.Group>
                  <Form.Group controlId="formBasicCheckbox">
                    <Form.Check type="checkbox" label="Check me out" />
                  </Form.Group>
                  <Button
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
    </>
  );
};

export default SignUpForm;
