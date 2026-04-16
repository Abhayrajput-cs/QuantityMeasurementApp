@RestController
@RequestMapping("/api/v1/users")
// @CrossOrigin(origins = "http://35.172.201.210")
    @CrossOrigin(origins = {
    "http://abhayqma.duckdns.org",
    "http://abhayqma.duckdns.org:8080"
})
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO dto) {
        try {
            String response = userService.register(dto);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO dto) {
        return ResponseEntity.ok(userService.login(dto));
    }

    @GetMapping("/google/success")
    public String googleLoginSuccess(@AuthenticationPrincipal OAuth2User user) {

        if (user == null) {
return "redirect:http://abhayqma.duckdns.org/login?error";        }

        String email = user.getAttribute("email");
        String name = user.getAttribute("name");

        String token = userService.googleLogin(email, name);

       return "redirect:http://abhayqma.duckdns.org/auth?token=" + token;
    }
}
