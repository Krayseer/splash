package ru.anykeyers.commonsapi;

/**
 * Название очереди сообщений
 */
public final class MessageQueue {

    /**
     * Очередь с созданными заказами
     */
    public static final String ORDER_CREATE = "order-create";

    /**
     * Очереб с удаленными заказами
     */
    public static final String ORDER_DELETE = "order-delete";

    /**
     * Очередь одобрения приглашений
     */
    public static final String INVITATION_APPLY = "invitation-apply";

    /**
     * Очередь назначения работника заказу
     */
    public static final String ORDER_EMPLOYEE_APPLY = "order-employee-apply";

}
