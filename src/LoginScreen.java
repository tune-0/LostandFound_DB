import javax.swing.*;
import java.awt.*;

public class LoginScreen extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin, btnCancel;
    private JCheckBox chkShowPassword;
    private UserDAO userDAO;
    private User loggedInUser = null;

    public LoginScreen() {
        userDAO = new UserDAO();
        initComponents();
    }

    private void initComponents() {
        setTitle("Login - Lost and Found System");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        // Main Panel with gradient background
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                int w = getWidth();
                int h = getHeight();
                Color color1 = new Color(41, 128, 185);
                Color color2 = new Color(52, 152, 219);
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, h, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        mainPanel.setLayout(new GridBagLayout());

        // Login Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
                BorderFactory.createEmptyBorder(40, 50, 40, 50)
        ));

        // Add subtle shadow effect
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(5, 5, 8, 8),
                formPanel.getBorder()
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Icon/Logo
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel lblIcon = new JLabel("📦");
        lblIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 60));
        lblIcon.setHorizontalAlignment(SwingConstants.CENTER);
        formPanel.add(lblIcon, gbc);

        // Title
        gbc.gridy = 1;
        JLabel lblTitle = new JLabel("Lost & Found System");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitle.setForeground(new Color(52, 73, 94));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        formPanel.add(lblTitle, gbc);

        // Subtitle
        gbc.gridy = 2;
        gbc.insets = new Insets(5, 10, 20, 10);
        JLabel lblSubtitle = new JLabel("Sign in to continue");
        lblSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblSubtitle.setForeground(new Color(127, 140, 141));
        lblSubtitle.setHorizontalAlignment(SwingConstants.CENTER);
        formPanel.add(lblSubtitle, gbc);

        // Username Section
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(15, 10, 5, 10);
        JLabel lblUsername = new JLabel("Username");
        lblUsername.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblUsername.setForeground(new Color(52, 73, 94));
        formPanel.add(lblUsername, gbc);

        gbc.gridy = 4;
        gbc.insets = new Insets(0, 10, 15, 10);
        txtUsername = new JTextField(20);
        txtUsername.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtUsername.setPreferredSize(new Dimension(280, 40));
        txtUsername.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        formPanel.add(txtUsername, gbc);

        // Password Section
        gbc.gridy = 5;
        gbc.insets = new Insets(5, 10, 5, 10);
        JLabel lblPassword = new JLabel("Password");
        lblPassword.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblPassword.setForeground(new Color(52, 73, 94));
        formPanel.add(lblPassword, gbc);

        gbc.gridy = 6;
        gbc.insets = new Insets(0, 10, 10, 10);
        txtPassword = new JPasswordField(20);
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtPassword.setPreferredSize(new Dimension(280, 40));
        txtPassword.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        formPanel.add(txtPassword, gbc);

        // Show Password Checkbox
        gbc.gridy = 7;
        gbc.insets = new Insets(0, 10, 15, 10);
        chkShowPassword = new JCheckBox("Show Password");
        chkShowPassword.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        chkShowPassword.setBackground(Color.WHITE);
        chkShowPassword.setFocusPainted(false);
        formPanel.add(chkShowPassword, gbc);

        // Login Button
        gbc.gridy = 8;
        gbc.insets = new Insets(15, 10, 10, 10);
        btnLogin = new JButton("LOGIN");
        btnLogin.setPreferredSize(new Dimension(280, 45));
        btnLogin.setBackground(new Color(46, 204, 113));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLogin.setFocusPainted(false);
        btnLogin.setBorderPainted(false);
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        formPanel.add(btnLogin, gbc);

        // Cancel Button
        gbc.gridy = 9;
        gbc.insets = new Insets(5, 10, 10, 10);
        btnCancel = new JButton("CANCEL");
        btnCancel.setPreferredSize(new Dimension(280, 45));
        btnCancel.setBackground(new Color(236, 240, 241));
        btnCancel.setForeground(new Color(52, 73, 94));
        btnCancel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnCancel.setFocusPainted(false);
        btnCancel.setBorderPainted(false);
        btnCancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        formPanel.add(btnCancel, gbc);

        // Info Panel at bottom
        gbc.gridy = 10;
        gbc.insets = new Insets(20, 10, 5, 10);
        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(new Color(236, 240, 241));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        JLabel lblInfo = new JLabel("<html><center><b>Default Credentials:</b><br>Username: <b>admin</b> | Password: <b>admin123</b></center></html>");
        lblInfo.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblInfo.setForeground(new Color(127, 140, 141));
        lblInfo.setHorizontalAlignment(SwingConstants.CENTER);
        infoPanel.add(lblInfo);
        formPanel.add(infoPanel, gbc);

        mainPanel.add(formPanel);
        add(mainPanel, BorderLayout.CENTER);

        // Action Listeners
        addActionListeners();

        // Add hover effects
        addHoverEffect(btnLogin, new Color(46, 204, 113), new Color(39, 174, 96));
        addHoverEffect(btnCancel, new Color(236, 240, 241), new Color(220, 225, 230));
    }

    private void addHoverEffect(JButton button, Color normalColor, Color hoverColor) {
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hoverColor);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(normalColor);
            }
        });
    }

    private void addActionListeners() {
        // Show/Hide password
        chkShowPassword.addActionListener(e -> {
            if (chkShowPassword.isSelected()) {
                txtPassword.setEchoChar((char) 0);
            } else {
                txtPassword.setEchoChar('•');
            }
        });

        // Login button
        btnLogin.addActionListener(e -> performLogin());

        // Cancel button
        btnCancel.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to exit?",
                    "Confirm Exit",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        // Enter key on password field
        txtPassword.addActionListener(e -> performLogin());

        // Enter key on username field
        txtUsername.addActionListener(e -> txtPassword.requestFocus());
    }

    private void performLogin() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());

        // Validation
        if (username.isEmpty()) {
            showError("Please enter your username!");
            txtUsername.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            showError("Please enter your password!");
            txtPassword.requestFocus();
            return;
        }

        // Show loading cursor
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        btnLogin.setEnabled(false);
        btnLogin.setText("LOGGING IN...");

        // Simulate slight delay for better UX
        Timer timer = new Timer(500, e -> {
            // Authenticate
            loggedInUser = userDAO.authenticateUser(username, password);

            setCursor(Cursor.getDefaultCursor());
            btnLogin.setEnabled(true);
            btnLogin.setText("LOGIN");

            if (loggedInUser != null) {
                // Success - fade out effect would be nice but keep it simple
                this.dispose();
                SwingUtilities.invokeLater(() -> {
                    LostAndFoundGUI mainApp = new LostAndFoundGUI(loggedInUser);
                    mainApp.setVisible(true);
                });
            } else {
                showError("Invalid username or password!");
                txtPassword.setText("");
                txtUsername.requestFocus();

                // Shake effect on error (simple version)
                Point originalLocation = getLocation();
                Timer shakeTimer = new Timer(50, null);
                final int[] shakeCount = {0};
                shakeTimer.addActionListener(ev -> {
                    if (shakeCount[0] < 6) {
                        int x = (shakeCount[0] % 2 == 0) ? 10 : -10;
                        setLocation(originalLocation.x + x, originalLocation.y);
                        shakeCount[0]++;
                    } else {
                        setLocation(originalLocation);
                        ((Timer)ev.getSource()).stop();
                    }
                });
                shakeTimer.start();
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this,
                message,
                "Login Error",
                JOptionPane.ERROR_MESSAGE);
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            LoginScreen loginScreen = new LoginScreen();
            loginScreen.setVisible(true);
        });
    }
}